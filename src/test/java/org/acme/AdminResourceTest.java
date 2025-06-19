package org.acme;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
class AdminResourceTest {

    @Test
    void testAdminEndpointProtected() {
        given()
          .when().get("/admin")
          .then()
             .statusCode(401);
    }

    @Test
    void testAdminEndpointWithAuth() {
        given()
          .auth().preemptive().basic("admin", "secret")
          .when().get("/admin")
          .then()
             .statusCode(200)
             .body(containsString("Welcome to the EquiScheduler Admin Interface"));
    }
}
