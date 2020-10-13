package com.example.demo.dao

import com.example.demo.po.Blog
import com.example.demo.po.Comment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface CommentRepository : JpaRepository<Comment, Long> {

    //    @Query("select c from Comment c where c.parentComment like ")
    fun getCommentsByParentCommentIsNullAndBlog_Id(blog_id: Long): List<Comment>


}