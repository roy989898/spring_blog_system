package com.example.demo.form

import com.example.demo.po.Blog
import com.example.demo.po.Tag
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class BlogInputForm(
        @field:NotNull(message = "please enter")
        @field:NotBlank(message = "please enter")
        var title: String?,
        @field:NotNull(message = "please enter")
        @field:NotBlank(message = "please enter")
        var content: String?,
        var firstPicture: String?,
        @field:NotNull(message = "please enter")
        @field:NotBlank(message = "please enter")
        var flag: String?,
        @field:NotNull
        var typeId: Long?,
        var recommend: Boolean?,
        var shareStatement: Boolean?,
        var appreciation: Boolean?,
        var commentabled: Boolean?,
        var published: Boolean?,
        var id: Long?,
        var tags: String?


) {
    fun toBlog(): Blog {
        val newBlog = Blog(id, title ?: "", content ?: "", firstPicture, flag ?: "", 0, appreciation
                ?: false, shareStatement ?: false, commentabled ?: false, published ?: false, recommend
                ?: false, Date(), Date(), null, emptyList(), null, emptyList<Tag>().toMutableSet()

        )


        return newBlog


    }
}