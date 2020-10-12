package com.example.demo.dao

import com.example.demo.po.Blog
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface BlogRepository : JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {


    @Query("select b from Blog b  where b.title like %?1% or b.content like %?1%")
    fun searchBlog(key: String, pageable: Pageable): Page<Blog>
}