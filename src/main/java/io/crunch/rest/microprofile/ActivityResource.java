package io.crunch.rest.microprofile;

import io.crunch.rest.shared.Activity;
import io.quarkus.rest.client.reactive.runtime.RestClientBuilderImpl;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.ext.QueryParamStyle;
import org.jboss.resteasy.reactive.client.api.LoggingScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * REST resource for retrieving activities using a programmatically created Quarkus REST client.
 * <p>
 * This class demonstrates how to manually configure and create a REST client using {@link RestClientBuilder}.
 * Most of the settings defined in this class, such as timeouts, headers, and query parameter styles,
 * can also be overridden through Quarkus configuration properties.
 * </p>
 */
@Path("/microprofile")
public class ActivityResource {

    private final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ActivityMicroprofileService activityService;

    /**
     * Constructs the REST client with custom configurations.
     *
     * @param url The base URL of the activity service, injected from Quarkus configuration.
     */
    public ActivityResource(
            @ConfigProperty(name = "quarkus.rest-client.activities-microprofile.url") String url) {
        this.activityService = ((RestClientBuilderImpl) RestClientBuilder.newBuilder())
                .baseUri(URI.create(url))
                .connectTimeout(1000L, TimeUnit.MILLISECONDS) // Connection timeout
                // .executorService(executorService) - Not used due to the non-blocking nature of the call
                .followRedirects(true) // Enable the following redirects
                .loggingScope(LoggingScope.ALL) // Log request and response details
                .loggingBodyLimit(50_000) // Limit logging of body content to 50 KB
                .header("x-other-header", "any-value") // Set a custom header
                .queryParamStyle(QueryParamStyle.MULTI_PAIRS) // Configure query parameter style
                .readTimeout(1000L, TimeUnit.MILLISECONDS) // Read timeout
                .build(ActivityMicroprofileService.class);
    }

    /**
     * Retrieves a list of activities in a non-blocking manner.
     * <p>
     * This method executes the request asynchronously, leveraging Mutiny's {@link Uni} for reactive execution.
     * The execution happens on the Quarkus event loop thread.
     * </p>
     *
     * @return A {@link Uni} emitting a list of activities when available.
     */
    @GET
    @Path("non-blocking")
    public Uni<List<Activity>> getActivities() {
        log.info("Get activities using microprofile builder non-blocking way");
        return activityService.getActivities();
    }

    /**
     * Retrieves a list of activities in a blocking manner.
     * <p>
     * This method explicitly waits for the response to arrive using {@link Uni#await()} and executes the
     * request on a worker thread instead of the Quarkus event loop.
     * </p>
     *
     * @return A list of activities retrieved from the remote service.
     */
    @GET
    @Path("blocking")
    public List<Activity> getActivitiesBlocking() {
        log.info("Get activities using microprofile builder blocking way");
        return activityService.getActivities().await().indefinitely();
    }
}
