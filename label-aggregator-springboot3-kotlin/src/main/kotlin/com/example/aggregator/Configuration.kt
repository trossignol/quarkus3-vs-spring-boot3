package com.example.aggregator

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class Configuration {

    @Bean
    fun getWebClient() = WebClient.create()
}