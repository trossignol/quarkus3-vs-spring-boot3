package org.acme.label.aggregator;

import java.util.List;

import org.acme.label.aggregator.LabelClient.LabelResult;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/api")
public class Resource {

    @RestClient
    LabelClient labelClient;

    @GET
    @Path("/{key}/sync")
    @Blocking
    public Result getSync(String key) {
        return Result.build(key, List.of(this.labelClient.getSync(key), this.labelClient.getSync(key),
                this.labelClient.getSync(key)));
    }

    @GET
    @Path("/{key}/async")
    @Blocking
    public Uni<Result> getAsync(String key) {
        return Uni.join()
                .all(this.labelClient.getAsync(key), this.labelClient.getAsync(key), this.labelClient.getAsync(key))
                .andCollectFailures().map(labels -> Result.build(key, labels));
    }

    public record Result(String key, List<String> labels) {
        static Result build(String key, List<LabelResult> results) {
            return new Result(key, results.stream().map(LabelResult::label).toList());
        }
    }
}