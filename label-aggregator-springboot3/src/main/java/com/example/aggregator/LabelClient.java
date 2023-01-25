package com.example.aggregator;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class LabelClient {
    private final WebClient client = WebClient.create("http://localhost:8090");

    @RegisterReflectionForBinding(LabelResult.class)
    public Mono<LabelResult> getAsync(String key) {
        return client.get()
                .uri("/api/" + key)
                .retrieve()
                .bodyToMono(LabelResult.class);
    }

    

}
