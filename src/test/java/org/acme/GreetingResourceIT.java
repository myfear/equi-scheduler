package org.acme;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusIntegrationTest;

@QuarkusIntegrationTest
@QuarkusTestResource(H2DatabaseTestResource.class)
class GreetingResourceIT extends GreetingResourceTest {
    // Execute the same tests but in packaged mode.
}
