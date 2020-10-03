package com.example.demo.service

import com.example.demo.po.Type
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface TypeService {


    fun save(type: Type): Type

    fun getType(id: Long): Type?
    fun getTypeNyName(name: String): Type?

    fun listType(pageable: Pageable): Page<Type>
    fun listType(): List<Type>
    fun updateType(id: Long, type: Type)

    fun deleteType(id: Long)
}