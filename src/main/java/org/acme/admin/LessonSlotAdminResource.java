package org.acme.admin;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.domain.LessonSlot;
import org.acme.domain.Instructor;
import org.acme.domain.Horse;
import org.acme.repository.LessonSlotRepository;
import org.acme.repository.InstructorRepository;
import org.acme.repository.HorseRepository;

import java.time.LocalDateTime;
import java.util.List;

@Path("/admin/lesson-slots")
@RolesAllowed("admin")
public class LessonSlotAdminResource {

    @Inject
    LessonSlotRepository repository;

    @Inject
    InstructorRepository instructorRepository;

    @Inject
    HorseRepository horseRepository;

    @Inject
    @Location("admin/lesson-slots")
    Template index;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance list() {
        List<LessonSlot> slots = repository.listAll();
        List<Instructor> instructors = instructorRepository.listAll();
        List<Horse> horses = horseRepository.listAll();
        return index.data("slots", slots)
                    .data("instructors", instructors)
                    .data("horses", horses);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public TemplateInstance create(@FormParam("startTime") String startTime,
                                   @FormParam("endTime") String endTime,
                                   @FormParam("instructorId") Long instructorId,
                                   @FormParam("horseId") Long horseId) {
        LessonSlot slot = new LessonSlot();
        slot.startTime = LocalDateTime.parse(startTime);
        slot.endTime = LocalDateTime.parse(endTime);
        slot.instructor = instructorRepository.findById(instructorId);
        if (horseId != null) {
            slot.horse = horseRepository.findById(horseId);
        }
        repository.persist(slot);
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
