package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.TestTransaction;
import jakarta.inject.Inject;
import org.acme.domain.Instructor;
import org.acme.domain.LessonSlot;
import org.acme.repository.InstructorRepository;
import org.acme.repository.LessonSlotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
class LessonSlotResourceTest {

    @Inject
    LessonSlotRepository repository;

    @Inject
    InstructorRepository instructorRepository;

    @BeforeEach
    @TestTransaction
    void setUp() {
        Instructor ins = new Instructor();
        ins.firstName = "Paul";
        instructorRepository.persist(ins);

        LessonSlot slot = new LessonSlot();
        slot.startTime = LocalDateTime.of(2024,1,1,9,0);
        slot.endTime = LocalDateTime.of(2024,1,1,10,0);
        slot.instructor = ins;
        repository.persist(slot);
    }

    @Test
    void testSchedulePage() {
        given()
          .when().get("/schedule")
          .then()
             .statusCode(200)
             .body(containsString("Available Lesson Slots"));
    }
}
