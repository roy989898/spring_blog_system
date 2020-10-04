package com.example.demo.form

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
        var flag: String,
        @field:NotNull
        var typeId: Long?,
        var recommend: Boolean?,
        var shareStatement: Boolean?,
        var appreciation: Boolean?,
        var commentabled: Boolean?,
        var published: Boolean?


)