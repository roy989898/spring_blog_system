package com.example.demo.service

import com.example.demo.form.BlogSearchForm
import com.example.demo.po.Blog
import com.example.demo.po.Tag
import com.example.demo.po.Type
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*
import kotlin.collections.ArrayList

interface TagService {


    fun getTag(id: Long): Optional<Tag>
    fun getTag(name: String): ArrayList<Tag>



}