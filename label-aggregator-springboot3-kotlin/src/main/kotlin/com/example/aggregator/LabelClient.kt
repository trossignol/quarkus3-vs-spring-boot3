package com.example.aggregator

import kotlinx.coroutines.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange

@Service
class LabelClient(private val client: WebClient) {
    @Value("\${label-client.url}") private lateinit var baseURL: String

    suspend fun getAsync(key: String): LabelResult =
            client.get().uri("$baseURL/${key}").awaitExchange { clientResponse ->
                clientResponse.awaitBody<LabelResult>()
            }

    data class LabelResult(val key: String, val label: String)
}
