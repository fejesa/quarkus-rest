package io.crunch.rest.simple;

import io.crunch.rest.shared.Activity;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Path("/simple")
public class ActivitySimpleResource {

    private final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ActivitySimpleService activitySimpleService;

    public ActivitySimpleResource(@RestClient ActivitySimpleService activitySimpleService) {
        this.activitySimpleService = activitySimpleService;
    }

    @GET
    public Uni<List<Activity>> getActivities() {
        log.info("Get activities using simple API");
        return activitySimpleService.getActivities();
    }
}
