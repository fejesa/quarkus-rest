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

/**
 * REST resource for retrieving activities using the Quarkus REST client.
 * <p>
 * This resource provides an endpoint that calls an external service to fetch activity data.
 * It utilizes the {@link ActivityService} REST client, which is injected using {@link RestClient}.
 * The method returns a reactive {@link Uni} with a list of {@link Activity} objects.
 * </p>
 * The default scope of this resource is {@link jakarta.enterprise.context.ApplicationScoped}.
 */
@Path("/simple")
public class ActivityResource {

    private final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @RestClient
    private ActivityService activityService;

    /**
     * REST client for interacting with the activity service.
     * This is injected by Quarkus and automatically configured via {@link RestClient}.
     */
    public ActivityResource(@RestClient ActivityService activityService) {
        this.activityService = activityService;
    }

    /**
     * Retrieves a list of activities from the external service.
     * <p>
     * This method delegates the call to {@link ActivityService#getActivities()},
     * which fetches activity data asynchronously. The response is logged before returning.
     * </p>
     *
     * @return A {@link Uni} emitting a list of activities when available.
     * @apiNote The method is executed asynchronously on the event loop.
     */
    @GET
    public Uni<List<Activity>> getActivities() {
        log.info("Get activities using generated client");
        return activityService.getActivities();
    }
}
