package org.acme.label.aggregator;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import io.quarkus.virtual.threads.VirtualThreads;
import org.acme.label.aggregator.LabelClient.LabelResult;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/api")
public class Resource {

    @RestClient
    LabelClient labelClient;

    @VirtualThreads
    ExecutorService executorService;

    @GET
    @Path("/{key}/sync")
    public Result getSync(String key) {
        return Result.build(key, List.of(this.labelClient.getSync(key), this.labelClient.getSync(key),
                this.labelClient.getSync(key)));
    }

    @GET
    @Path("/{key}/async")
    public Uni<Result> getAsync(String key) {
        return Uni.join()
                .all(this.labelClient.getAsync(key), this.labelClient.getAsync(key), this.labelClient.getAsync(key))
                .andCollectFailures().map(labels -> Result.build(key, labels));
    }

    @GET
    @Path("/{key}/sync/vt")
    @RunOnVirtualThread
    public Result getSyncVirtualThread(String key) {
        return Result.build(key, List.of(this.labelClient.getSync(key), this.labelClient.getSync(key),
                this.labelClient.getSync(key)));
    }

    @GET
    @Path("/{key}/async/vt")
    @RunOnVirtualThread
    public Result getAsyncVirtualThread(String key) throws CancellationException,InterruptedException, ExecutionException {
        List<Callable<LabelResult>> toCall = Arrays.asList(
                () -> this.labelClient.getSync(key),
                () -> this.labelClient.getSync(key),
                () -> this.labelClient.getSync(key)
        );

        return Result.build(key,executorService.invokeAll(toCall)
                .stream()
                .map(this::getLabelStringFromFuture).collect(Collectors.toList()));

    }

    private LabelResult getLabelStringFromFuture (Future<LabelResult> labelResultFuture){
        try {
            return labelResultFuture.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public record Result(String key, List<String> labels) {
        static Result build(String key, List<LabelResult> results) {
            return new Result(key, results.stream().map(LabelResult::label).toList());
        }
    }
}