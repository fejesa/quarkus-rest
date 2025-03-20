package io.crunch.rest.quarkus;

import io.crunch.rest.shared.Activity;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;

import java.util.List;

@Path("/activities")
@RegisterClientHeaders(ActivityQuarkusHeadersFactory.class)
public interface ActivityQuarkusService {

    @GET
    @Produces(MediaType.APPLICATION_JSON) // no need to set if quarkus-rest-client-jackson is installed
    @Consumes(MediaType.APPLICATION_JSON)
    Uni<List<Activity>> getActivities();
}
