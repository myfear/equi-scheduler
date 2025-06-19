package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

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
          .auth().preemptive().basic("admin", "secret")
          .formParam("firstName", "Mary")
          .formParam("lastName", "Poppins")
          .when().post("/admin/instructors")
          .then()
             .statusCode(200);

        given()
          .auth().preemptive().basic("admin", "secret")
          .when().get("/admin/instructors")
          .then()
             .statusCode(200)
             .body(containsString("Mary"));
    }
}
