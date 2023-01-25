package com.example.aggregator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class Resource {

    @Autowired
    LabelClient labelClient;

    @GetMapping("/api/{key}/async")
    public Mono<Result> getAsync(@PathVariable("key") String key) {
        return Mono.zip(this.labelClient.getAsync(key), this.labelClient.getAsync(key), this.labelClient.getAsync(key))
                .map(labels -> List.of(labels.getT1(), labels.getT2(), labels.getT3()))
                .map(labels -> Result.build(key, labels));
    }

    public record Result(String key, List<String> labels) {
        static Result build(String key, List<LabelResult> results) {
            return new Result(key, results.stream().map(LabelResult::getLabel).toList());
        }
    }
}
