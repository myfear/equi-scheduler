package org.acme.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Access {
    @Id
    public String username;
    public String role;
    public String password;
}
