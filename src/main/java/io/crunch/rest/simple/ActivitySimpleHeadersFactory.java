package io.crunch.rest.simple;

import io.quarkus.rest.client.reactive.ReactiveClientHeadersFactory;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;

import java.util.Random;

@ApplicationScoped
public class ActivitySimpleHeadersFactory extends ReactiveClientHeadersFactory {

    private final Random random = new Random();

    @Override
    public Uni<MultivaluedMap<String, String>> getHeaders(MultivaluedMap<String, String> incomingHeaders, MultivaluedMap<String, String> clientOutgoingHeaders) {
        int v = random.nextInt(1000);
        var headers = new MultivaluedHashMap<String, String>();
        headers.add("x-activity-simple", Integer.toString(v));
        return Uni.createFrom().item(headers);
    }
}
