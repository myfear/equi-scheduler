package org.acme;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.acme.domain.Horse;
import org.acme.repository.HorseRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class HorseRepositoryTest {

    @Inject
    HorseRepository repository;

    @Test
    @TestTransaction
    void crudHorse() {
        Horse horse = new Horse();
        horse.name = "Betsy";
        repository.persist(horse);

        assertNotNull(horse.id);

        Horse found = repository.findById(horse.id);
        assertEquals("Betsy", found.name);

        found.name = "Bella";
        repository.persist(found);
        repository.flush();

        Horse updated = repository.findById(horse.id);
        assertEquals("Bella", updated.name);

        repository.delete(updated);
        assertNull(repository.findById(horse.id));
    }
}
