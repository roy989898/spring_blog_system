package com.example.demo.service

import com.example.demo.dao.TagRepository
import com.example.demo.po.Tag
import java.util.*
import kotlin.collections.ArrayList

class TagServiceImp(val tagRepository: TagRepository) : TagService {
    override fun getTag(id: Long): Optional<Tag> {
        return tagRepository.findById(id)
    }

    override fun getTag(name: String): ArrayList<Tag> {
        return tagRepository.findAllByNameContains(name)
    }


}