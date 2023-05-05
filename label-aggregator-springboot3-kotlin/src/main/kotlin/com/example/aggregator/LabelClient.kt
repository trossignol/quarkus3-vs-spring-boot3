package com.example.aggregator

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class LabelClient(private val restTemplate: RestTemplate) {
    @Value("\${label-client.url}") private val baseURL: String? = null

    suspend fun getAsync(key: String): LabelResult =
            withContext(Dispatchers.IO) {
                (restTemplate.getForObject(baseURL + "/{key}", LabelResult::class.java, key))!!
            }

    data class LabelResult(val key: String, val label: String)
}
