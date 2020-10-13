package com.example.demo.service

import com.example.demo.form.BlogSearchForm
import com.example.demo.po.Blog
import com.example.demo.po.Type
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

interface BlogService {


    fun getBlog(id: Long): Blog?

    fun listBlog(pageable: Pageable, blogForm: BlogSearchForm): Page<Blog>
    fun listBlog(pageable: Pageable): Page<Blog>
    fun listBlog(key: String, pageable: Pageable): Page<Blog>
    fun listBlog( sort:Sort): List<Blog>
    fun listBlogTop(size: Int): List<Blog>
    fun saveBlog(blog: Blog): Blog

    fun updateBlog(id: Long, blog: Blog): Blog
    fun updateBlogVIew(id: Long): Int
    fun deleteBlog(id: Long)
}