package io.crunch.rest.quarkus;

import io.crunch.rest.shared.Activity;
import io.quarkus.rest.client.reactive.QuarkusRestClientBuilder;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpClientOptions;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.ext.QueryParamStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * This class demonstrates a way of configuring a Quarkus REST client using QuarkusRestClientBuilder.
 * The client settings are configured programmatically but can be overridden via Quarkus configuration properties.
 */
@Path("/quarkus")
public class ActivityResource {

    private final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ActivityService activityService;

    /**
     * Constructs an ActivityResource and initializes the REST client with custom configurations.
     *
     * @param url The base URL for the ActivityService, which can be overridden via Quarkus configuration.
     */
    public ActivityResource(@ConfigProperty(name = "quarkus.rest-client.activities-quarkus.url") String url) {
        this.activityService = QuarkusRestClientBuilder.newBuilder()
                .baseUri(URI.create(url))
                .clientHeadersFactory(new ActivityHeadersFactory())
                .connectTimeout(100L, TimeUnit.MILLISECONDS)
                .readTimeout(100L, TimeUnit.MILLISECONDS)
                .httpClientOptions(new HttpClientOptions()
                        .setMaxHeaderSize(1024)  // Max header size in bytes
                        .setMaxPoolSize(10)  // Maximum number of concurrent connections
                        .setKeepAlive(true)  // Keep connections open
                        .setPipelining(false)  // Disable HTTP pipelining
                        .setConnectTimeout(1)  // Connection timeout in milliseconds
                        .setIdleTimeout(10))  // Idle timeout in seconds
                .userAgent("Quarkus-Rest-Client")
                .property("some.config.key", "value")  // Custom property for additional configurations
                .queryParamStyle(QueryParamStyle.MULTI_PAIRS)  // Query parameter formatting style
                .build(ActivityService.class);
    }

    /**
     * Retrieves a list of activities using the configured REST client.
     *
     * @return A Uni representing the asynchronous response containing the list of activities.
     */
    @GET
    public Uni<List<Activity>> getActivities() {
        log.info("Get activities using quarkus builder non-blocking way");
        return activityService.getActivities();
    }
}
