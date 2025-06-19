package org.acme.student;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

@Path("/student")
public class StudentResource {

    @Inject
    @Location("student/index")
    Template index;

    @GET
    @Produces(MediaType.TEXT_HTML)
    @RolesAllowed("student")
    public TemplateInstance index(@Context SecurityContext ctx) {
        return index.data("user", ctx.getUserPrincipal().getName());
    }
}
