package com.example.demo.dao

import com.example.demo.po.Blog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface BlogRepository : JpaRepository<Blog, Long>,JpaSpecificationExecutor<Blog> {
}