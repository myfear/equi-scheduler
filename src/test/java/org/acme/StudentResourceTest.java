package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
class StudentResourceTest {

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
          .auth().preemptive().basic("student", "generated")
          .when().get("/student")
          .then()
             .statusCode(200)
             .body(containsString("Please change your password"));
    }
}
