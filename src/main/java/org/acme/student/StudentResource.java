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
import jakarta.ws.rs.QueryParam;
import io.quarkus.elytron.security.common.BcryptUtil;

import org.acme.domain.Access;
import org.acme.domain.Student;
import org.acme.domain.Instructor;
import org.acme.domain.LessonSlot;
import org.acme.repository.StudentRepository;
import org.acme.repository.InstructorRepository;
import org.acme.repository.LessonSlotRepository;

@Path("/student")
public class StudentResource {

    @Inject
    @Location("student/index")
    Template index;

    @Inject
    @Location("student/profile")
    Template profile;

    @Inject
    @Location("student/calendar")
    Template calendar;

    @Inject
    StudentRepository repository;

    @Inject
    InstructorRepository instructorRepository;

    @Inject
    LessonSlotRepository lessonSlotRepository;

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
            Access access = student.access;
            access.password = BcryptUtil.bcryptHash(password);
        student.access= access;
        }
        repository.persist(student);
        repository.flush();
        return profile.data("student", student);
    }

    @GET
    @Path("/calendar")
    @Produces(MediaType.TEXT_HTML)
    @RolesAllowed("student")
    public TemplateInstance calendar(@QueryParam("instructorId") Long instructorId) {
        var instructors = instructorRepository.listAll();
        var slots = instructorId == null ? java.util.Collections.<LessonSlot>emptyList() :
                lessonSlotRepository.findByInstructorId(instructorId);
        return calendar.data("instructors", instructors)
                      .data("slots", slots)
                      .data("selectedInstructorId", instructorId);
    }
}
