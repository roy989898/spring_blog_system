package com.example.demo.errorHandle

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class RestException (override val message: String): RuntimeException() {
}