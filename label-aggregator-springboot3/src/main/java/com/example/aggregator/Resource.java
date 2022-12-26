package com.example.aggregator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.aggregator.LabelClient.LabelResult;

import lombok.Getter;
import reactor.core.publisher.Mono;

@RestController
public class Resource {

    @Autowired
    LabelClient labelClient;

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/api/{key}/async")
    public Mono<Result> getAsync(@PathVariable("key") String key) {
        return Mono.zip(this.labelClient.getAsync(key), this.labelClient.getAsync(key), this.labelClient.getAsync(key))
                .map(labels -> List.of(labels.getT1(), labels.getT2(), labels.getT3()))
                .map(labels -> new Result(key, labels));
    }

    @Getter
    public class Result {
        private final String key;
        private final List<String> labels;

        public Result(String key, List<LabelResult> results) {
            this.key = key;
            this.labels = results.stream().map(LabelResult::getLabel).toList();
        }
    }
}
