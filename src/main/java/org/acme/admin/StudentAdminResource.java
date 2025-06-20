package org.acme.admin;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.acme.domain.Access;
import org.acme.domain.Student;
import org.acme.repository.StudentRepository;
import io.quarkus.elytron.security.common.BcryptUtil;

import java.util.List;

@Path("/admin/students")
@RolesAllowed("admin")
public class StudentAdminResource {

    @Inject
    StudentRepository repository;

    @Inject
    @Location("admin/students")
    Template index;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance list() {
        List<Student> students = repository.listAll();
        return index.data("students", students);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public TemplateInstance create(@FormParam("firstName") String firstName,
                                   @FormParam("lastName") String lastName,
                                   @FormParam("email") String email,
                                   @FormParam("phone") String phone,
                                   @FormParam("username") String username,
                                   @FormParam("password") String password) {
        Student student = new Student();
        student.firstName = firstName;
        student.lastName = lastName;
        student.email = email;
        student.phone = phone;
        Access studentAccess = new Access();
        studentAccess.username = username;
        studentAccess.password = BcryptUtil.bcryptHash(password);
        student.access = studentAccess;
        repository.persist(student);
        return list();
    }
}
