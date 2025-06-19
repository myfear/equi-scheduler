package org.acme;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.acme.domain.Student;
import org.acme.domain.Instructor;
import org.acme.repository.StudentRepository;
import org.acme.repository.InstructorRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class StudentRepositoryTest {

    @Inject
    StudentRepository repository;

    @Inject
    InstructorRepository instructorRepository;

    @Test
    @TestTransaction
    void crudStudent() {
        Instructor ins1 = new Instructor();
        ins1.firstName = "Sam";
        instructorRepository.persist(ins1);

        Instructor ins2 = new Instructor();
        ins2.firstName = "Jamie";
        instructorRepository.persist(ins2);

        Student student = new Student();
        student.firstName = "Alex";
        student.preferedInstructor = ins1;
        repository.persist(student);

        assertNotNull(student.id);

        Student found = repository.findById(student.id);
        assertEquals("Alex", found.firstName);
        assertEquals(ins1.id, found.preferedInstructor.id);

        found.firstName = "Taylor";
        found.preferedInstructor = ins2;
        repository.persist(found);
        repository.flush();

        Student updated = repository.findById(student.id);
        assertEquals("Taylor", updated.firstName);
        assertEquals(ins2.id, updated.preferedInstructor.id);

        repository.delete(updated);
        assertNull(repository.findById(student.id));
    }
}
