package io.crunch.rest.simple;

import io.crunch.rest.shared.Activity;
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
@RegisterRestClient(configKey = "activities-simple-api")
@RegisterClientHeaders(ActivitySimpleHeadersFactory.class)
public interface ActivitySimpleService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Uni<List<Activity>> getActivities();
}
