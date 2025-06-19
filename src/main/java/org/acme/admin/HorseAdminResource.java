package org.acme.admin;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.domain.Horse;
import org.acme.repository.HorseRepository;

import java.util.List;

@Path("/admin/horses")
@RolesAllowed("admin")
public class HorseAdminResource {

    @Inject
    HorseRepository repository;

    @Inject
    @Location("admin/horses")
    Template index;

    @Inject
    @Location("admin/horse-form")
    Template form;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance list() {
        List<Horse> horses = repository.listAll();
        return index.data("horses", horses);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public TemplateInstance create(@FormParam("name") String name,
                                   @FormParam("breed") String breed,
                                   @FormParam("age") Integer age,
                                   @FormParam("notes") String notes,
                                   @FormParam("maxDailyHours") Integer maxDailyHours) {
        Horse horse = new Horse();
        horse.name = name;
        horse.breed = breed;
        horse.age = age;
        horse.notes = notes;
        horse.maxDailyHours = maxDailyHours;
        repository.persist(horse);
        return list();
    }

    @GET
    @Path("{id}/edit")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance editForm(@PathParam("id") Long id) {
        Horse horse = repository.findById(id);
        return form.data("horse", horse);
    }

    @POST
    @Path("{id}/edit")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public TemplateInstance update(@PathParam("id") Long id,
                                   @FormParam("name") String name,
                                   @FormParam("breed") String breed,
                                   @FormParam("age") Integer age,
                                   @FormParam("notes") String notes,
                                   @FormParam("maxDailyHours") Integer maxDailyHours) {
        Horse horse = repository.findById(id);
        horse.name = name;
        horse.breed = breed;
        horse.age = age;
        horse.notes = notes;
        horse.maxDailyHours = maxDailyHours;
        repository.persist(horse);
        repository.flush();
        return list();
    }

    @POST
    @Path("{id}/delete")
    @Transactional
    public TemplateInstance delete(@PathParam("id") Long id) {
        repository.deleteById(id);
        repository.flush();
        return list();
    }
}
