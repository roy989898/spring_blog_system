package com.example.demo.form

import com.example.demo.po.Type

data class BlogSearchForm(
        var page: Int?,
        var title: String?,
        var typeId: Int?,
        var recommend: Boolean?
)