package io.crunch.rest.simple;

import io.crunch.rest.shared.Activity;
import io.crunch.rest.shared.CustomApplicationException;
import io.quarkus.rest.client.reactive.ClientExceptionMapper;
import io.quarkus.rest.client.reactive.ClientQueryParam;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

/**
 * REST client interface for interacting with the "activities" endpoint.
 * <p>
 * This interface defines a REST client using Quarkus REST Client Reactive.
 * It enables automatic client generation based on the interface definition.
 * The client is configured with {@code activities-simple} as its config key.
 * Custom headers are added via {@link ActivitySimpleHeadersFactory}.
 * </p>
 */
@Path("/activities")
@RegisterRestClient(configKey = "activities-simple")
@RegisterClientHeaders(ActivitySimpleHeadersFactory.class)
public interface ActivitySimpleService {

    /**
     * Retrieves a list of activities from the remote service.
     * <p>
     * This method performs an HTTP GET request to fetch activity data in JSON format.
     * The {@code other-param} query parameter is added automatically with a value of "other".
     * The method returns a reactive {@link Uni} containing a list of {@link Activity} objects.
     * </p>
     *
     * @return A {@link Uni} that emits a list of activities when available.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON) // No need to set if quarkus-rest-client-jackson is installed
    @Consumes(MediaType.APPLICATION_JSON)
    @ClientQueryParam(name = "other-param", value = "other")
    Uni<List<Activity>> getActivities();

    /**
     * Maps HTTP responses to custom exceptions.
     * <p>
     * This method is used to handle error responses from the remote service.
     * If the HTTP status is not 200, it throws a {@link CustomApplicationException}
     * with an appropriate error message.
     * </p>
     *
     * @param response The HTTP response received from the remote service.
     * @return A {@link CustomApplicationException} if the response status is not 200, otherwise {@code null}.
     */
    @ClientExceptionMapper
    static CustomApplicationException convertToException(Response response) {
        return response.getStatus() != 200 ? new CustomApplicationException("The remote service responded with HTTP " + response.getStatus()) : null;
    }
}
