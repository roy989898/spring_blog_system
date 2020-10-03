package com.example.demo.form

import com.example.demo.po.Type

data class BlogSearchForm(

        var title: String?,
        var type: Type?,
        var recommend: Boolean
)