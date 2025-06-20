package org.acme.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

/**
 * Student entity representing a rider.
 */

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String firstName;
    public String lastName;
    public String email;
    public String phone;

    @OneToOne(cascade = jakarta.persistence.CascadeType.ALL)
    public Access access;

    /**
     * Optional preferred instructor for this student.
     */
    @ManyToOne
    public Instructor preferedInstructor;
}
