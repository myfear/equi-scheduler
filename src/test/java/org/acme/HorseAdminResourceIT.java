package org.acme;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusIntegrationTest;

@QuarkusIntegrationTest
@QuarkusTestResource(CustomH2DatabaseTestResource.class)
class HorseAdminResourceIT extends HorseAdminResourceTest {
    // tests are inherited
}
