package com.example.demo.api.Response

import com.example.demo.po.Comment
import com.example.demo.po.Tag
import com.example.demo.po.Type
import com.example.demo.po.User
import java.util.*
import javax.persistence.*

data class RestBlogDetailResponse(

        var id: Long?,
        var title: String,

        var content: String,

        var firstPicture: String?,
        var flag: String,
        var vies: Int,
        var appreciation: Boolean,
        var shareStatement: Boolean,
        var commentabled: Boolean,
        var published: Boolean,
        var recommend: Boolean,

        var createTime: Date,

        var updateTime: Date,


        var type: Type?,

        var comments: List<Comment>,

        var tags: MutableSet<Tag>

) {
}