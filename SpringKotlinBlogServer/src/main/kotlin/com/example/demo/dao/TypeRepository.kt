package com.example.demo.dao

import com.example.demo.po.Type
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface TypeRepository : JpaRepository<Type, Long> {
    fun findByName(name: String): Optional<Type>



}