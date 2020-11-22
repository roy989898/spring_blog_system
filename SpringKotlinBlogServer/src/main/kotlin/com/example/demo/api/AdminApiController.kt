package com.example.demo.api

import com.example.demo.api.Response.GetBlogListResponse
import com.example.demo.po.Blog
import com.example.demo.po.Tag
import com.example.demo.service.BlogService
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("/api/admin")
class AdminApiController(private val blogService: BlogService) {

    private val LOGGER = LoggerFactory.getLogger(AdminApiController::class.java)


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/blogs")
    fun getBlogList(): List<GetBlogListResponse> {
        val sort = Sort.by(Sort.Direction.DESC, "updateTime")
        val blogs = blogService.listBlog(sort)
        val result = blogs.map {
            GetBlogListResponse(it.id, it.title, it.recommend, it.published, it.updateTime)
        }
        return result
    }
}