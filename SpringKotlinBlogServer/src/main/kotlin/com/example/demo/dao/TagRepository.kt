package com.example.demo.dao

import com.example.demo.po.Tag
import kotlinx.coroutines.selects.select
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.domain.Pageable
import java.util.*

interface TagRepository : JpaRepository<Tag, Long> {

    fun findAllByNameContains(name: String): ArrayList<Tag>
    fun findTagByNameEquals(name: String): Optional<Tag>


    @Query("select t from Tag t  ")
    fun findAllBySQl( var1: Pageable): List<Tag>
}