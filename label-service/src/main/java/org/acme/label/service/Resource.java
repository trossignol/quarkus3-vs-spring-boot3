package org.acme.label.service;

import java.time.Duration;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.commons.lang3.RandomUtils;

import io.smallrye.mutiny.Uni;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;

@Path("/api")
public class Resource {

    @GET
    @Path("/{key}")
    @SneakyThrows
    public Uni<Result> get(String key) {
        return Uni.createFrom().item(new Result(key, "label-for-key-" + key))
                .onItem().delayIt().by(Duration.ofMillis(100 + RandomUtils.nextInt(0, 100)));
    }

    @Getter
    @AllArgsConstructor
    public class Result {
        private String key;
        private String label;
    }
}