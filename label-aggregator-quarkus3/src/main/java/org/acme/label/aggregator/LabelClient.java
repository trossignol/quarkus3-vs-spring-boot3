package org.acme.label.aggregator;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.smallrye.mutiny.Uni;
import lombok.Data;

@RegisterRestClient(configKey = "label-client")
public interface LabelClient {
    
    @GET
    @Path("/{key}")
    LabelResult getSync(@PathParam("key") String key);

    @GET
    @Path("/{key}")
    Uni<LabelResult> getAsync(@PathParam("key") String key);

    @Data
    public class LabelResult {
        private String key;
        private String label;
    }
}
