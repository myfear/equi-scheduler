package org.acme;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class InstructorAdminResourceTest {



    @Test
    void testListProtected() {
        given()
          .when().get("/admin/instructors")
          .then()
             .statusCode(401);
    }

    @Test
    void testListWithAuth() {
        given()
          .auth().preemptive().basic("admin", "password")
          .formParam("firstName", "Mary")
          .formParam("lastName", "Poppins")
          .formParam("username", "mary")
          .formParam("password", "pwd")
          .when().post("/admin/instructors")
          .then()
             .statusCode(200);

        given()
          .auth().preemptive().basic("admin", "password")
          .when().get("/admin/instructors")
          .then()
             .statusCode(200)
             .body(containsString("Mary"));
    }
}
