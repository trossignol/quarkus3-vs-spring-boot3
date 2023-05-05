package com.example.aggregator

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class Resource(private val labelClient: LabelClient) {
    @GetMapping("/") fun index(@RequestParam("name") name: String) = "Hello, $name!"

    @GetMapping("/api/{key}/async")
    suspend fun getLabels(
            @PathVariable("key") key: String,
            @RequestParam(value = "nb", defaultValue = "3") nb: Int
    ): Result {
        var labels = (1..nb).map { i -> labelClient.getAsync(key + i).label }
        var results: Result = Result(key, labels)
        return results
    }

    data class Result(val key: String, val labels: List<String>)
}
