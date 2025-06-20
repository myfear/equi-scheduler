package org.acme;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.repository.LessonSlotRepository;

@Path("/schedule")
public class LessonSlotResource {

    @Inject
    LessonSlotRepository repository;

    @Inject
    @Location("schedule/index")
    Template index;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance list() {
        return index.data("slots", repository.listAll());
    }
}
