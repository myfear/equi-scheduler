package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.domain.Student;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class StudentRepository implements PanacheRepository<Student> {
}
