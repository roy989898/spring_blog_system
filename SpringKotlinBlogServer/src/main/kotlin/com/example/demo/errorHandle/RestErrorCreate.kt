package com.example.demo.errorHandle

fun createRestError(message: String): RestErrorResponse {
    return RestErrorResponse(message, null)
}