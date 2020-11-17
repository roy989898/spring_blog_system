package com.example.demo.errorHandle.RestAPi

data class DefaultError(
        val timestamp: String?,
        val status: Int?,
        val error: String?,
        val message: String?,
        val path: String?


) {
}