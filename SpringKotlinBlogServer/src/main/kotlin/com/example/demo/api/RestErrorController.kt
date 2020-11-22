/*
package com.example.demo.api

import com.example.demo.errorHandle.RestAPi.DefaultError
import com.example.demo.errorHandle.RestErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest


@RestControllerAdvice
class RestErrorController {
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleError(ex: Exception, request: WebRequest): RestErrorResponse {
        return RestErrorResponse("", DefaultError(ex.message))

    }
}*/
