package org.acme.admin;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/admin")
public class AdminResource {

    @Inject
    @Location("admin/index")
    Template index;

    @GET
    @Produces(MediaType.TEXT_HTML)
    @RolesAllowed("admin")
    public TemplateInstance index() {
        return index.instance();
    }
}
