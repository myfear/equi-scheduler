package org.acme;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import org.acme.CustomH2DatabaseTestResource;

@QuarkusIntegrationTest
@QuarkusTestResource(CustomH2DatabaseTestResource.class)
class HorseAdminResourceIT extends HorseAdminResourceTest {
    // tests are inherited
}
