package org.acme.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class LessonSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public LocalDateTime startTime;
    public LocalDateTime endTime;

    @ManyToOne
    public Instructor instructor;

    @ManyToOne
    public Horse horse;
}
