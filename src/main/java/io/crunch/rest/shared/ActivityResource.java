package io.crunch.rest.shared;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@Path("/api")
public class ActivityResource {

    @GET
    @Path("/activities")
    public RestResponse<List<Activity>> getActivities() {
        return RestResponse.ok(List.of(new Activity("Running", 10), new Activity("Swimming", 20)));
    }
}
