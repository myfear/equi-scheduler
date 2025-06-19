package org.acme;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.acme.domain.Instructor;
import org.acme.repository.InstructorRepository;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
class InstructorAdminResourceTest {

    @Inject
    InstructorRepository repository;

    @Test
    void testListProtected() {
        given()
          .when().get("/admin/instructors")
          .then()
             .statusCode(401);
    }

    @Test
    @TestTransaction
    void testListWithAuth() {
        Instructor ins = new Instructor();
        ins.firstName = "Mary";
        ins.lastName = "Poppins";
        repository.persist(ins);

        given()
          .auth().preemptive().basic("admin", "secret")
          .when().get("/admin/instructors")
          .then()
             .statusCode(200)
             .body(containsString("Mary"));
    }
}
