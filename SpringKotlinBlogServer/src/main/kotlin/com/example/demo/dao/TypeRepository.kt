package com.example.demo.dao

import com.example.demo.po.Type
import org.springframework.data.jpa.repository.JpaRepository

interface TypeRepository:JpaRepository<Type,Long> {
}