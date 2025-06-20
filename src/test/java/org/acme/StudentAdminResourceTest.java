package org.acme;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

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
          .auth().preemptive().basic("admin", "password")
          .formParam("firstName", "John")
          .formParam("lastName", "Doe")
          .formParam("email", "john@example.com")
          .formParam("username", "john")
          .formParam("password", "pwd")
          .when().post("/admin/students")
          .then()
             .statusCode(200);

        given()
          .auth().preemptive().basic("admin", "password")
          .when().get("/admin/students")
          .then()
             .statusCode(200)
             .body(containsString("John"));
    }
}
