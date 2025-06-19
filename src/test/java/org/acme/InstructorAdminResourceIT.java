package org.acme;

import io.quarkus.test.common.QuarkusTestResource;
import org.acme.CustomH2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusIntegrationTest;

@QuarkusIntegrationTest
@QuarkusTestResource(CustomH2DatabaseTestResource.class)
class InstructorAdminResourceIT extends InstructorAdminResourceTest {
    // tests are inherited
}
