package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.domain.Admin;
import org.acme.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
class InstructorAdminResourceTest {

    @Inject
    AdminRepository adminRepository;

    @BeforeEach
    @Transactional
    void setUp() {
        if (adminRepository.count() == 0) {
            Admin admin = new Admin();
            admin.username = "admin";
            admin.password = BcryptUtil.bcryptHash("secret");
            adminRepository.persist(admin);
            adminRepository.flush();
        }
    }


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
          .formParam("username", "mary")
          .formParam("password", "pwd")
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
