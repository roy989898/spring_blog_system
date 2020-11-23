package com.example.demo.api.Response

import com.example.demo.po.Type
import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

data class RestClientBlogResponse(
        val id: Long,
        val title: String,
        val brief: String,
        val author: String,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        val createDate: Date,
        val views: Int,
        val type: RestTypeListResponse?


) {
}