package org.acme;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.time.LocalDateTime;
import org.acme.domain.Instructor;
import org.acme.domain.Horse;
import org.acme.domain.LessonSlot;
import org.acme.repository.InstructorRepository;
import org.acme.repository.HorseRepository;
import org.acme.repository.LessonSlotRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class LessonSlotRepositoryTest {

    @Inject
    LessonSlotRepository lessonRepo;

    @Inject
    InstructorRepository instructorRepo;

    @Inject
    HorseRepository horseRepo;

    @Test
    @TestTransaction
    void crudLessonSlot() {
        Instructor ins = new Instructor();
        ins.firstName = "Amy";
        instructorRepo.persist(ins);

        Horse horse = new Horse();
        horse.name = "Flash";
        horseRepo.persist(horse);

        LessonSlot slot = new LessonSlot();
        slot.startTime = LocalDateTime.now();
        slot.endTime = slot.startTime.plusHours(1);
        slot.instructor = ins;
        slot.horse = horse;
        lessonRepo.persist(slot);

        assertNotNull(slot.id);

        LessonSlot found = lessonRepo.findById(slot.id);
        assertEquals(ins.id, found.instructor.id);

        found.endTime = found.endTime.plusMinutes(30);
        lessonRepo.persist(found);
        lessonRepo.flush();

        LessonSlot updated = lessonRepo.findById(slot.id);
        assertEquals(found.endTime, updated.endTime);

        lessonRepo.delete(updated);
        assertNull(lessonRepo.findById(slot.id));
    }
}
