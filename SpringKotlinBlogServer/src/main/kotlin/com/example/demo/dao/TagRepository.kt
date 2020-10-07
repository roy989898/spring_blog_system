package com.example.demo.dao

import com.example.demo.po.Blog
import com.example.demo.po.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface TagRepository : JpaRepository<Tag, Long> {

    fun findAllByNameContains(name: String): ArrayList<Tag>
}