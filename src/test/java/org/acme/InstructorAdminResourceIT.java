package org.acme;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusIntegrationTest;

@QuarkusIntegrationTest
@QuarkusTestResource(H2DatabaseTestResource.class)
class InstructorAdminResourceIT extends InstructorAdminResourceTest {
    // tests are inherited
}
