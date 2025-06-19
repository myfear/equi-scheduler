package org.acme.security;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.IdentityProvider;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.request.UsernamePasswordAuthenticationRequest;
import io.quarkus.security.runtime.QuarkusPrincipal;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.domain.Admin;
import org.acme.domain.Instructor;
import org.acme.domain.Student;
import org.acme.repository.AdminRepository;
import org.acme.repository.InstructorRepository;
import org.acme.repository.StudentRepository;

@ApplicationScoped
public class DatabaseIdentityProvider implements IdentityProvider<UsernamePasswordAuthenticationRequest> {

    @Inject
    AdminRepository adminRepository;
    @Inject
    InstructorRepository instructorRepository;
    @Inject
    StudentRepository studentRepository;

    @Override
    public Class<UsernamePasswordAuthenticationRequest> getRequestType() {
        return UsernamePasswordAuthenticationRequest.class;
    }

    @Override
    public Uni<SecurityIdentity> authenticate(UsernamePasswordAuthenticationRequest request, AuthenticationRequestContext context) {
        String username = request.getUsername();
        String password = new String(request.getPassword().getPassword());
        return context.runBlocking(() -> doAuth(username, password));
    }

    @Transactional
    @ActivateRequestContext
    SecurityIdentity doAuth(String username, String password) {
        Admin admin = adminRepository.find("username", username).firstResult();
        if (admin != null && BcryptUtil.matches(password, admin.password)) {
            return buildIdentity(username, "admin");
        }
        Instructor instructor = instructorRepository.find("username", username).firstResult();
        if (instructor != null && BcryptUtil.matches(password, instructor.password)) {
            return buildIdentity(username, "instructor");
        }
        Student student = studentRepository.find("username", username).firstResult();
        if (student != null && BcryptUtil.matches(password, student.password)) {
            return buildIdentity(username, "student");
        }
        return null;
    }

    private SecurityIdentity buildIdentity(String username, String role) {
        QuarkusSecurityIdentity.Builder builder = QuarkusSecurityIdentity.builder();
        builder.setPrincipal(new QuarkusPrincipal(username));
        builder.addRole(role);
        return builder.build();
    }
}
