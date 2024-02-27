package org.acme.label.service;

import java.time.Duration;
import java.util.Random;

import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/api")
public class Resource {

    private final Random random = new Random();

    @GET
    @Path("/sync/{key}")
    public Result getSync(String key) throws InterruptedException {
        Thread.sleep(100 + random.nextInt(0, 100));
        return new Result(key, "label-for-key-" + key);
    }

    @GET
    @Path("/{key}")
    @RunOnVirtualThread
    public Result getSyncVirtualThread(String key) throws InterruptedException {
        Thread.sleep(100 + random.nextInt(0, 100));
        return new Result(key, "label-for-key-" + key);
    }

    @GET
    @Path("/async/{key}")
    public Uni<Result> getAsync(String key) {
        return Uni.createFrom().item(new Result(key, "label-for-key-" + key))
                .onItem().delayIt().by(Duration.ofMillis(100 + random.nextInt(0, 100)));
    }

    public record Result(String key, String label) {
    }
}