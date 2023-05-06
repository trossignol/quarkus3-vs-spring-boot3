package com.example.aggregator

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class Configuration : WebMvcConfigurer {

    @Bean
    fun getWebClient() = WebClient.create()

    @Bean
    fun webMvcConfigurer(): WebMvcConfigurer = object : WebMvcConfigurer {
        override fun addViewControllers(registry: ViewControllerRegistry) {
            registry.addViewController("/").setViewName("redirect:/swagger-ui.html")
        }

        override fun configureContentNegotiation(configurer: ContentNegotiationConfigurer) {
            configurer.defaultContentType(MediaType.APPLICATION_JSON)
        }
    }

    @Bean
    fun myOpenAPI(): OpenAPI =
            OpenAPI().info(Info()
                    .title("Label Aggregator test")
                    .description("Test Spring-Boot+Kotlin vs Spring-Boot+Java, Quarkus, ..."))
}