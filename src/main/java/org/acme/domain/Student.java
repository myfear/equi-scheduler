package org.acme.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

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

    public String username;
    public String password;

    /**
     * Optional preferred instructor for this student.
     */
    @ManyToOne
    public Instructor preferedInstructor;
}
