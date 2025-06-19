package org.acme.admin;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.transaction.Transactional;
import org.acme.domain.Instructor;
import org.acme.repository.InstructorRepository;

import java.util.List;

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
                                   @FormParam("availability") String availability,
                                   @FormParam("defaultSlotDuration") Integer defaultSlotDuration) {
        Instructor instructor = new Instructor();
        instructor.firstName = firstName;
        instructor.lastName = lastName;
        instructor.email = email;
        instructor.phone = phone;
        instructor.availability = availability;
        instructor.defaultSlotDuration = defaultSlotDuration;
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
                                   @FormParam("availability") String availability,
                                   @FormParam("defaultSlotDuration") Integer defaultSlotDuration,
                                   @FormParam("active") boolean active) {
        Instructor instructor = repository.findById(id);
        instructor.firstName = firstName;
        instructor.lastName = lastName;
        instructor.email = email;
        instructor.phone = phone;
        instructor.availability = availability;
        instructor.defaultSlotDuration = defaultSlotDuration;
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

