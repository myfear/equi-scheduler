package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import org.acme.domain.Access;
import org.acme.domain.Student;
import org.acme.domain.Instructor;
import org.acme.domain.LessonSlot;
import org.acme.repository.StudentRepository;
import org.acme.repository.InstructorRepository;
import org.acme.repository.LessonSlotRepository;
import io.quarkus.test.TestTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
class StudentResourceTest {

    @Inject
    StudentRepository studentRepository;

    @Inject
    InstructorRepository instructorRepository;

    @Inject
    LessonSlotRepository lessonSlotRepository;

    Long instructorId;

    @BeforeEach
    @TestTransaction
    void setUp() {
        Instructor ins = new Instructor();
        ins.firstName = "Cal";
        instructorRepository.persist(ins);

        LessonSlot slot = new LessonSlot();
        slot.startTime = java.time.LocalDateTime.of(2024,1,1,9,0);
        slot.endTime = java.time.LocalDateTime.of(2024,1,1,10,0);
        slot.instructor = ins;
        lessonSlotRepository.persist(slot);

        instructorId = ins.id;
    }


    @Test
    void testStudentEndpointProtected() {
        given()
          .when().get("/student")
          .then()
             .statusCode(401);
    }

    @Test
    void testStudentEndpointWithAuth() {
        given()
          .auth().preemptive().basic("student", "password")
          .when().get("/student")
          .then()
             .statusCode(200)
             .body(containsString("Please change your password"));
    }

    @Test
    void testCalendarProtected() {
        given()
          .when().get("/student/calendar")
          .then()
             .statusCode(401);
    }

    @Test
    void testCalendarWithAuth() {
        given()
          .auth().preemptive().basic("student", "password")
          .when().get("/student/calendar?instructorId=" + instructorId)
          .then()
             .statusCode(200)
             .body(containsString("Lesson Calendar"));
    }
}
