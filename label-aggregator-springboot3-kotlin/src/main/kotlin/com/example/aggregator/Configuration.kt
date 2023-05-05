package com.example.aggregator

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@Configuration
class Configuration {

    @Bean
    fun getRestTemplate() = RestTemplate()

}