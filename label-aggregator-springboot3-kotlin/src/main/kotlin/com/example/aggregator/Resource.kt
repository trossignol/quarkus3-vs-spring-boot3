package com.example.aggregator

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Resource() {
    @GetMapping("/")
    fun index(@RequestParam("name") name: String) = "Hello, $name!"
}
