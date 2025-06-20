package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.domain.LessonSlot;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class LessonSlotRepository implements PanacheRepository<LessonSlot> {

    public java.util.List<LessonSlot> findByInstructorId(Long instructorId) {
        return list("instructor.id", instructorId);
    }
}
