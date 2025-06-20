package org.acme.admin;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.transaction.Transactional;
import io.quarkus.elytron.security.common.BcryptUtil;

import org.acme.domain.Access;
import org.acme.domain.Instructor;
import org.acme.repository.InstructorRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/admin/instructors")
@RolesAllowed("admin")
public class InstructorAdminResource {

    @Inject
    InstructorRepository repository;

    @Inject
    @Location("admin/instructors")
    Template index;

    @Inject
    @Location("admin/instructor-form")
    Template form;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance list() {
        List<Instructor> instructors = repository.listAll();
        return index.data("instructors", instructors);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public TemplateInstance create(@FormParam("firstName") String firstName,
                                   @FormParam("lastName") String lastName,
                                   @FormParam("email") String email,
                                   @FormParam("phone") String phone,
                                   @FormParam("username") String username,
                                   @FormParam("password") String password,
                                   @FormParam("availability") List<String> availability,
                                   @FormParam("slotDurations") List<String> slotDurations) {
        Instructor instructor = new Instructor();
        instructor.firstName = firstName;
        instructor.lastName = lastName;
        instructor.email = email;
        instructor.phone = phone;
        Access instructorAccess = new Access();
        instructorAccess.username = username;
        instructorAccess.password = BcryptUtil.bcryptHash(password);
        instructor.access = instructorAccess;
        instructor.availability = availability.stream()
                .map(String::toUpperCase)
                .map(DayOfWeek::valueOf)
                .collect(Collectors.toSet());
        instructor.slotDurations = slotDurations.stream()
                .map(Integer::valueOf)
                .collect(Collectors.toSet());
        repository.persist(instructor);
        return list();
    }

    @GET
    @Path("{id}/edit")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance editForm(@PathParam("id") Long id) {
        Instructor instructor = repository.findById(id);
        return form.data("instructor", instructor);
    }

    @POST
    @Path("{id}/edit")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public TemplateInstance update(@PathParam("id") Long id,
                                   @FormParam("firstName") String firstName,
                                   @FormParam("lastName") String lastName,
                                   @FormParam("email") String email,
                                   @FormParam("phone") String phone,
                                   @FormParam("availability") List<String> availability,
                                   @FormParam("slotDurations") List<String> slotDurations,
                                   @FormParam("active") boolean active) {
        Instructor instructor = repository.findById(id);
        instructor.firstName = firstName;
        instructor.lastName = lastName;
        instructor.email = email;
        instructor.phone = phone;
        instructor.availability = availability.stream()
                .map(String::toUpperCase)
                .map(DayOfWeek::valueOf)
                .collect(Collectors.toSet());
        instructor.slotDurations = slotDurations.stream()
                .map(Integer::valueOf)
                .collect(Collectors.toSet());
        instructor.active = active;
        repository.persist(instructor);
        repository.flush();
        return list();
    }

    @POST
    @Path("{id}/toggle")
    @Transactional
    public TemplateInstance toggle(@PathParam("id") Long id) {
        Instructor instructor = repository.findById(id);
        instructor.active = !instructor.active;
        repository.persist(instructor);
        repository.flush();
        return list();
    }
}

