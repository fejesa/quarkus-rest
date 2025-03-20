package io.crunch.rest.simple;

import io.quarkus.rest.client.reactive.ReactiveClientHeadersFactory;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;

import java.util.Random;

/**
 * Factory for generating custom headers in Quarkus REST client requests.
 * <p>
 * This class extends {@link ReactiveClientHeadersFactory} to dynamically
 * add custom headers to outgoing REST client requests.
 * </p>
 */
@ApplicationScoped
public class ActivitySimpleHeadersFactory extends ReactiveClientHeadersFactory {

    private final Random random = new Random();

    /**
     * Generates additional headers for outgoing REST client requests.
     * <p>
     * This method adds a custom header {@code x-activity-simple} with a
     * random integer value between 0 and 999. The header can be used
     * by the remote service for tracking or load distribution purposes.
     * </p>
     *
     * @param incomingHeaders Headers received from the incoming request (if applicable).
     * @param clientOutgoingHeaders Headers already set by the client before this factory is applied.
     * @return A {@link Uni} emitting the modified set of headers.
     */
    @Override
    public Uni<MultivaluedMap<String, String>> getHeaders(MultivaluedMap<String, String> incomingHeaders, MultivaluedMap<String, String> clientOutgoingHeaders) {
        int v = random.nextInt(1000);
        var headers = new MultivaluedHashMap<String, String>();
        headers.add("x-activity-simple", Integer.toString(v));
        return Uni.createFrom().item(headers);
    }
}
