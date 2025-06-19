package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.domain.Instructor;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class InstructorRepository implements PanacheRepository<Instructor> {
}
