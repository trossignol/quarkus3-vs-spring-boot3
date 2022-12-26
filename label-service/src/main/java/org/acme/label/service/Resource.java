package org.acme.label.service;

import java.time.Duration;
import java.util.Random;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/api")
public class Resource {

    private final Random random = new Random();

    @GET
    @Path("/{key}")
    public Uni<Result> get(String key) {
        return Uni.createFrom().item(new Result(key, "label-for-key-" + key))
                .onItem().delayIt().by(Duration.ofMillis(100 + random.nextInt(0, 100)));
    }

    public record Result(String key, String label) {
    }
}