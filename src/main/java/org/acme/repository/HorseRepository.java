package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.domain.Horse;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class HorseRepository implements PanacheRepository<Horse> {
}
