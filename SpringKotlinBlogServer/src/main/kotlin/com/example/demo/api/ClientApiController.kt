package com.example.demo.api

import com.example.demo.api.Response.RestBlogDetailResponse
import com.example.demo.api.Response.RestClientBlogResponse
import com.example.demo.api.Response.RestClientCategoryResponse
import com.example.demo.api.Response.RestTypeListResponse
import com.example.demo.errorHandle.NotFoundException
import com.example.demo.getBrief
import com.example.demo.service.BlogService
import com.example.demo.service.TagService
import com.example.demo.service.TypeService
import com.example.demo.service.UserService
import org.springframework.data.domain.Sort
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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


    @GetMapping("/blogs/{id}")
    fun getBlogDetail(@PathVariable id: Long): RestBlogDetailResponse {
        val blog = blogService.getBlog(id)


        if (blog != null) {
            val comment = blog.comments.map {
                it.removeAllRelation()
                return@map it
            }

            val tag = blog.tags.map {
                it.blogs = emptyList()
                return@map it
            }

            return RestBlogDetailResponse(
                    blog.id, blog.title, blog.content, blog.firstPicture, blog.flag, blog.vies, blog.appreciation, blog.shareStatement, blog.commentabled, blog.published, blog.recommend, blog.createTime, blog.updateTime,
                    blog.type?.removeAllRelation(), comment, tag.toMutableSet()
            )

        } else {
            throw NotFoundException("blog not found")
        }


    }

    @GetMapping("/blogs/view/{id}")
    fun getUpdateBlogView(@PathVariable id: Long) {
        blogService.updateBlogVIew(id)

    }


    @GetMapping("/category")
    fun getCategory(): List<RestClientCategoryResponse> {
//        val sort = Sort.by(Sort.Direction.DESC, "updateTime")
        val types = typeService.listType().map {
            val blogNumber = (it.blogs ?: emptyList()).size
            RestClientCategoryResponse(it.id, it.name, blogNumber)
        }

        return types
    }


}