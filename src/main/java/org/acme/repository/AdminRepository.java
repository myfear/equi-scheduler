package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.domain.Admin;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class AdminRepository implements PanacheRepository<Admin> {
}
