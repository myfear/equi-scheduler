package org.acme;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import org.acme.domain.Access;
import org.acme.domain.Admin;
import org.acme.domain.Horse;
import org.acme.domain.Instructor;
import org.acme.repository.AdminRepository;
import org.acme.repository.HorseRepository;
import org.acme.repository.InstructorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
class LessonSlotAdminResourceTest {



    @Inject
    InstructorRepository instructorRepository;

    @Inject
    HorseRepository horseRepository;

    Long instructorId;
    Long horseId;

    @BeforeEach
    @Transactional
    void setUp() {


        Instructor ins = new Instructor();
        ins.firstName = "Tina";
        instructorRepository.persist(ins);
        instructorId = ins.id;

        Horse horse = new Horse();
        horse.name = "Bolt";
        horseRepository.persist(horse);
        horseId = horse.id;

        instructorRepository.flush();
        horseRepository.flush();
    }

    @Test
    void testListProtected() {
        given()
          .when().get("/admin/lesson-slots")
          .then()
             .statusCode(401);
    }

    @Test
    void testCreateAndListWithAuth() {
        given()
          .auth().preemptive().basic("admin", "password")
          .formParam("startTime", "2024-01-01T10:00")
          .formParam("endTime", "2024-01-01T11:00")
          .formParam("instructorId", instructorId)
          .formParam("horseId", horseId)
          .when().post("/admin/lesson-slots")
          .then()
             .statusCode(200);

        given()
          .auth().preemptive().basic("admin", "password")
          .when().get("/admin/lesson-slots")
          .then()
             .statusCode(200)
             .body(containsString("Bolt"));
    }
}
