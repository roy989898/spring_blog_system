package com.example.demo.form

data class BlogInputForm(
        var title: String?,
        var content: String?,
        var firstPicture: String?,
        var flag: String?,
        var typeId: Long?,
        var image: String?,
        var recommend: Boolean,
        var shareStatement: Boolean,
        var appreciation: Boolean,
        var commentabled: Boolean


)