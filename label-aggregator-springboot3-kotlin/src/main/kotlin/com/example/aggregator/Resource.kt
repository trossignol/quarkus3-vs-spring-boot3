package com.example.aggregator

import kotlinx.coroutines.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class Resource(private val labelClient: LabelClient) {

    @GetMapping("/api/{key}/async")
    suspend fun getAsync(
            @PathVariable("key") key: String,
            @RequestParam(value = "nb", defaultValue = "3") nb: Int
    ): Result {
        val scope = CoroutineScope(Dispatchers.Default)
        return Result(
                key,
                (1..nb).map { scope.async { labelClient.getAsync(key + it).label } }.awaitAll()
        )
    }

    data class Result(val key: String, val labels: List<String>)
}
