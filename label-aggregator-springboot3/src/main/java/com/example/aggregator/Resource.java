package com.example.aggregator;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class Resource {

    @Autowired
    LabelClient labelClient;

    @GetMapping("/api/{key}/async")
    public Mono<Result> getAsync(@PathVariable("key") String key,
            @RequestParam(value = "nb", defaultValue = "3") int nb) {
        return Mono.zip(
                IntStream.range(0, nb).mapToObj(i -> this.labelClient.getAsync(key + "-" + i)).toList(),
                r -> Stream.of(r).map(LabelResult.class::cast).toList())
                .map(list -> Result.build(key, list));
    }

    public record Result(String key, List<String> labels) {
        static Result build(String key, List<LabelResult> results) {
            return new Result(key, results.stream().map(LabelResult::getLabel).toList());
        }
    }
}
