package com.example.demo.service

import com.example.demo.po.Tag
import java.util.*
import kotlin.collections.ArrayList

interface TagService {


    fun getTag(id: Long): Optional<Tag>
    fun getTagLike(name: String): ArrayList<Tag>

    fun getTag(name: String): Optional<Tag>


}