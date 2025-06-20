package org.acme.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String firstName;
    public String lastName;
    public String email;
    public String phone;

    @OneToOne(cascade = jakarta.persistence.CascadeType.ALL)
    public Access access;

    @ElementCollection
    public Set<Integer> slotDurations;

    /**
     * Days of the week the instructor is generally available.
     */
    @ElementCollection
    @Enumerated(EnumType.STRING)
    public Set<DayOfWeek> availability;

    /**
     * Flag indicating if this instructor can currently be booked.
     */
    public boolean active = true;
}
