package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
class StudentAdminResourceTest {

    @Test
    void testListProtected() {
        given()
          .when().get("/admin/students")
          .then()
             .statusCode(401);
    }

    @Test
    void testCreateAndListWithAuth() {
        given()
          .auth().preemptive().basic("admin", "secret")
          .formParam("firstName", "John")
          .formParam("lastName", "Doe")
          .formParam("email", "john@example.com")
          .when().post("/admin/students")
          .then()
             .statusCode(200);

        given()
          .auth().preemptive().basic("admin", "secret")
          .when().get("/admin/students")
          .then()
             .statusCode(200)
             .body(containsString("John"));
    }
}
