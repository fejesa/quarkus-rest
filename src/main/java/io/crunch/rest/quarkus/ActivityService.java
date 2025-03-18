package io.crunch.rest.quarkus;

import io.crunch.rest.shared.Activity;
import io.crunch.rest.simple.ActivityHeadersFactory;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/activities")
@RegisterRestClient(configKey = "activities-quarkus")
@RegisterClientHeaders(ActivityHeadersFactory.class)
public interface ActivityService {

    @GET
    @Produces(MediaType.APPLICATION_JSON) // no need to set if quarkus-rest-client-jackson is installed
    @Consumes(MediaType.APPLICATION_JSON)
    Uni<List<Activity>> getActivities();
}
