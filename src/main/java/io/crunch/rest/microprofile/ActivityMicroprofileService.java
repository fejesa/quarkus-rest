package io.crunch.rest.microprofile;

import io.crunch.rest.shared.Activity;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;

import java.util.List;

@Path("/activities")
@RegisterClientHeaders(ActivityMicroprofileHeadersFactory.class)
public interface ActivityMicroprofileService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Uni<List<Activity>> getActivities();
}
