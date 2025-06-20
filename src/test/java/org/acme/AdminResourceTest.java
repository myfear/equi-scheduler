package org.acme;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
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
                .auth().preemptive().basic("admin", "password")
                .when().get("/admin")
                .then()
                .statusCode(200)
                .body(containsString("Welcome to the EquiScheduler Admin Interface"));
    }
}
