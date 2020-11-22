package com.example.demo.api

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("/api/admin")
class AdminApiController {

    private val LOGGER = LoggerFactory.getLogger(AdminApiController::class.java)
}