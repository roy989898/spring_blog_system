package com.example.demo.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("/api/public")
class HelloController {


    @GetMapping()
    fun index(): String {
        return "Hello POM"
    }
}