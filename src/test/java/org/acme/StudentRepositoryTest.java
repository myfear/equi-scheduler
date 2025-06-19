package org.acme;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.acme.domain.Student;
import org.acme.repository.StudentRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class StudentRepositoryTest {

    @Inject
    StudentRepository repository;

    @Test
    @TestTransaction
    void crudStudent() {
        Student student = new Student();
        student.firstName = "Alex";
        repository.persist(student);

        assertNotNull(student.id);

        Student found = repository.findById(student.id);
        assertEquals("Alex", found.firstName);

        found.firstName = "Taylor";
        repository.persist(found);
        repository.flush();

        Student updated = repository.findById(student.id);
        assertEquals("Taylor", updated.firstName);

        repository.delete(updated);
        assertNull(repository.findById(student.id));
    }
}
