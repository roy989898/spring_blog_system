package com.example.demo.dao

import com.example.demo.po.Blog
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface BlogRepository : JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {


    @Query("select b from Blog b  where b.title like %?1% or b.content like %?1%")
    fun searchBlog(key: String, pageable: Pageable): Page<Blog>


    @Query("select b from Blog b  where b.title like %?1% or b.type.name like %?1% ")
    fun searchBlogByTitleAndType(key: String): List<Blog>

    @Transactional
    @Modifying
    @Query("update Blog b set b.vies=b.vies+1 where b.id = ?1")
    fun updateBlogView(id: Long ):Int

    fun findBlogsByIdIn(ids:List<Long>):List<Blog>


    fun findBlogsByTitleContaining(title: String):List<Blog>




}