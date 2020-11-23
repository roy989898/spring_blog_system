package com.example.demo.api

import com.example.demo.api.Response.RestClientBlogResponse
import com.example.demo.api.Response.RestTypeListResponse
import com.example.demo.getBrief
import com.example.demo.service.BlogService
import com.example.demo.service.TagService
import com.example.demo.service.TypeService
import com.example.demo.service.UserService
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("/api/client")
class ClientApiController(private val blogService: BlogService,
                          private val tagService: TagService, private val userService: UserService,
                          private val typeService: TypeService) : BaseRestController() {


    @GetMapping("/blogs")
    fun getClientBlogList(): List<RestClientBlogResponse> {
        val sort = Sort.by(Sort.Direction.DESC, "createTime")
        val allBlog = blogService.listBlog(sort)


        val result = allBlog.map { blog ->
            val newType = blog.type?.let {
                RestTypeListResponse(it.id, it.name)
            }
            RestClientBlogResponse(blog.id ?: -1, blog.title, blog.contentToPlainText().getBrief(), blog.user?.nickname
                    ?: "", blog.createTime, blog.vies, newType
            )
        }


        return result

    }

}