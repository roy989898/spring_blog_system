package com.example.demo.dao

import com.example.demo.po.Tag
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TagRepository : JpaRepository<Tag, Long> {

    fun findAllByNameContains(name: String): ArrayList<Tag>
    fun findTagByNameEquals(name: String): Optional<Tag>
}