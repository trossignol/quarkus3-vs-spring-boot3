package com.example.aggregator

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "Get label", description = "Get label resource")
class Resource(private val labelClient: LabelClient) {

    @Operation(description = "Get aggregated labels for input key")
    @GetMapping("/api/{key}/async")
    suspend fun getAsync(
            @PathVariable(name = "key", required = true) @Parameter(description = "Key for labels") key: String,
            @RequestParam(name = "nb", defaultValue = "5") @Parameter(description = "Nb of labels requested") nb: Int
    ): Result {
        val scope = CoroutineScope(Dispatchers.Default)
        return Result(
                key,
                (1..nb).map { scope.async { labelClient.getAsync(key + it).label } }.awaitAll()
        )
    }

    @Schema(description = "Result labels")
    data class Result(
            @field:Schema(description = "Input key") val key: String,
            @field:Schema(description = "Generated labels") val labels: List<String>
    )
}
