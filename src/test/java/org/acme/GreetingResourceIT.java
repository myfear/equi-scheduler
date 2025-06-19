package org.acme;

import io.quarkus.test.common.QuarkusTestResource;
import org.acme.CustomH2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusIntegrationTest;

@QuarkusIntegrationTest
@QuarkusTestResource(CustomH2DatabaseTestResource.class)
class GreetingResourceIT extends GreetingResourceTest {
    // Execute the same tests but in packaged mode.
}
