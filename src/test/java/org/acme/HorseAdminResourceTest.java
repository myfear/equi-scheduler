package org.acme;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.domain.Admin;
import org.acme.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
class HorseAdminResourceTest {

    @Inject
    AdminRepository adminRepository;

    @BeforeEach
    @Transactional
    void setUp() {
        Admin admin = adminRepository.find("username", "admin").firstResult();
        if (admin == null) {
            admin = new Admin();
            admin.username = "admin";
        }
        admin.password = BcryptUtil.bcryptHash("secret");
        adminRepository.persist(admin);
        adminRepository.flush();
    }

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
          .auth().preemptive().basic("admin", "secret")
          .formParam("name", "Bella")
          .formParam("breed", "Quarter")
          .when().post("/admin/horses")
          .then()
             .statusCode(200);

        given()
          .auth().preemptive().basic("admin", "secret")
          .when().get("/admin/horses")
          .then()
             .statusCode(200)
             .body(containsString("Bella"));
    }
}
