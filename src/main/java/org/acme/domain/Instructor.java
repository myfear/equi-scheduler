package org.acme.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String firstName;
    public String lastName;
    public String email;
    public String phone;
    public Integer defaultSlotDuration;

    /**
     * Text description of the instructor's general availability.
     */
    public String availability;

    /**
     * Flag indicating if this instructor can currently be booked.
     */
    public boolean active = true;
}
