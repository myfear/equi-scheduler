package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import org.acme.domain.Access;
import org.acme.domain.Student;
import org.acme.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
class StudentResourceTest {

    @Inject
    StudentRepository studentRepository;


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
}
