package org.acme.student;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import io.quarkus.elytron.security.common.BcryptUtil;
import org.acme.domain.Student;
import org.acme.repository.StudentRepository;

@Path("/student")
public class StudentResource {

    @Inject
    @Location("student/index")
    Template index;

    @Inject
    @Location("student/profile")
    Template profile;

    @Inject
    StudentRepository repository;

    @GET
    @Produces(MediaType.TEXT_HTML)
    @RolesAllowed("student")
    public TemplateInstance index(@Context SecurityContext ctx) {
        return index.data("user", ctx.getUserPrincipal().getName());
    }

    @GET
    @Path("/profile")
    @Produces(MediaType.TEXT_HTML)
    @RolesAllowed("student")
    public TemplateInstance profile(@Context SecurityContext ctx) {
        Student student = repository.find("username", ctx.getUserPrincipal().getName()).firstResult();
        return profile.data("student", student);
    }

    @POST
    @Path("/profile")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    @RolesAllowed("student")
    public TemplateInstance update(@Context SecurityContext ctx,
                                   @FormParam("firstName") String firstName,
                                   @FormParam("lastName") String lastName,
                                   @FormParam("email") String email,
                                   @FormParam("phone") String phone,
                                   @FormParam("password") String password) {
        Student student = repository.find("username", ctx.getUserPrincipal().getName()).firstResult();
        student.firstName = firstName;
        student.lastName = lastName;
        student.email = email;
        student.phone = phone;
        if (password != null && !password.isEmpty()) {
            student.password = BcryptUtil.bcryptHash(password);
        }
        repository.persist(student);
        repository.flush();
        return profile.data("student", student);
    }
}
