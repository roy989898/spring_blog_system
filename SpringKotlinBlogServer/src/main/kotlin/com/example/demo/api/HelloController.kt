package com.example.demo.api

import com.example.demo.errorHandle.RestErrorResponse
import com.example.demo.errorHandle.RestException
import com.example.demo.errorHandle.createRestError
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController()
@RequestMapping("/api/public")
class HelloController {

    private val LOGGER = LoggerFactory.getLogger(HelloController::class.java)

    @GetMapping()
    fun index(): String {
        return "Hello POM"
    }

    @GetMapping("/error")
    fun error(): String {
        throw Exception("in error")

    }

    @ExceptionHandler(value = [Exception::class])
    fun handleException(request: HttpServletRequest, e: Exception): RestErrorResponse {
        LOGGER.error("Request Url :${request.requestURI}, Exception: ${e.message}")


        return createRestError(e.message ?: "")

    }
}