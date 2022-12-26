package org.acme.label.aggregator;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@RegisterRestClient(configKey = "label-client")
public interface LabelClient {

    @GET
    @Path("/{key}")
    LabelResult getSync(@PathParam("key") String key);

    @GET
    @Path("/{key}")
    Uni<LabelResult> getAsync(@PathParam("key") String key);

    public record LabelResult(String key, String label) {
    }
}
