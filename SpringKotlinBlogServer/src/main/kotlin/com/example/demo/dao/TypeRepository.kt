package com.example.demo.dao

import com.example.demo.po.Tag
import com.example.demo.po.Type
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface TypeRepository : JpaRepository<Type, Long> {
    fun findByName(name: String): Optional<Type>

    @Query("select t from Type t  ")
    fun findAllBySQl(var1: Pageable): List<Type>

}