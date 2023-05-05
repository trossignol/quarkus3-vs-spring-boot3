package com.example.aggregator

import kotlinx.coroutines.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange

@Service
class LabelClient(private val client: WebClient) {
    @Value("\${label-client.url}")
    private lateinit var baseURL: String

    suspend fun getAsync(key: String): LabelResult =
        client.get().uri("$baseURL/$key").awaitExchange { clientResponse ->
            clientResponse.awaitBody<LabelResult>()
        }


    suspend fun getAsyncAll(key: String, nb: Int = 3): List<String> {
        val results = mutableListOf<String>()
        CoroutineScope(Dispatchers.Default).run {
            results.addAll((1..nb).map {
                async {
                    getOneLabel(key, it)
                }
            }.awaitAll())
        }
        return results
    }

    private suspend fun getOneLabel(key: String, it: Int): String =
        client.get().uri("$baseURL/${key + it}").awaitExchange { clientResponse ->
            clientResponse.awaitBody<LabelResult>().label
        }

    data class LabelResult(val key: String, val label: String)
}
