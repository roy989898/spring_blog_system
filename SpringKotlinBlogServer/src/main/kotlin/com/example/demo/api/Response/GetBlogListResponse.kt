package com.example.demo.api.Response

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

data class GetBlogListResponse(
        val id: Long?,
        val title: String,
        var recommend: Boolean,
        var published: Boolean,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
        var updateTime: Date

)