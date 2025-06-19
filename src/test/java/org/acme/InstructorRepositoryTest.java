package org.acme;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.acme.domain.Instructor;
import org.acme.repository.InstructorRepository;
import java.time.DayOfWeek;
import java.util.Set;
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
        ins.availability = Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY);
        ins.slotDurations = Set.of(30, 60);
        repository.persist(ins);

        assertNotNull(ins.id);

        Instructor found = repository.findById(ins.id);
        assertEquals("John", found.firstName);
        assertTrue(found.active);

        found.firstName = "Jane";
        found.active = false;
        found.availability = Set.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
        found.slotDurations = Set.of(90);
        repository.persist(found);
        repository.flush();

        Instructor updated = repository.findById(ins.id);
        assertEquals("Jane", updated.firstName);
        assertFalse(updated.active);
        assertEquals(Set.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY), updated.availability);
        assertEquals(Set.of(90), updated.slotDurations);

        repository.delete(updated);
        assertNull(repository.findById(ins.id));
    }
}
