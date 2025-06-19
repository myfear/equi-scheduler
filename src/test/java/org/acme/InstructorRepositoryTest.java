package org.acme;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.acme.domain.Instructor;
import org.acme.repository.InstructorRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class InstructorRepositoryTest {

    @Inject
    InstructorRepository repository;

    @Test
    @TestTransaction
    void crudInstructor() {
        Instructor ins = new Instructor();
        ins.firstName = "John";
        ins.lastName = "Doe";
        ins.availability = "Weekdays";
        repository.persist(ins);

        assertNotNull(ins.id);

        Instructor found = repository.findById(ins.id);
        assertEquals("John", found.firstName);
        assertTrue(found.active);

        found.firstName = "Jane";
        found.active = false;
        found.availability = "Weekends";
        repository.persist(found);
        repository.flush();

        Instructor updated = repository.findById(ins.id);
        assertEquals("Jane", updated.firstName);
        assertFalse(updated.active);
        assertEquals("Weekends", updated.availability);

        repository.delete(updated);
        assertNull(repository.findById(ins.id));
    }
}
