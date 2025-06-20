package org.acme;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class HorseAdminResourceTest {

    @Test
    void testListProtected() {
        given()
          .when().get("/admin/horses")
          .then()
             .statusCode(401);
    }

    @Test
    void testCreateAndListWithAuth() {
        given()
          .auth().preemptive().basic("admin", "password")
          .formParam("name", "Bella")
          .formParam("breed", "Quarter")
          .when().post("/admin/horses")
          .then()
             .statusCode(200);

        given()
          .auth().preemptive().basic("admin", "password")
          .when().get("/admin/horses")
          .then()
             .statusCode(200)
             .body(containsString("Bella"));
    }
}
