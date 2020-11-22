package com.example.demo.api

import com.example.demo.api.Response.GetBlogListResponse
import com.example.demo.errorHandle.RestAPi.DefaultError
import com.example.demo.errorHandle.RestErrorResponse
import com.example.demo.form.BlogInputForm
import com.example.demo.form.BlogSearchForm
import com.example.demo.service.BlogService
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.WebRequest
import org.springframework.web.server.ResponseStatusException
import java.lang.RuntimeException
import java.util.stream.Collectors
import javax.validation.Valid


@RestController()
@RequestMapping("/api/admin")
class AdminApiController(private val blogService: BlogService) {

    private val LOGGER = LoggerFactory.getLogger(AdminApiController::class.java)

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleError(ex: Exception, request: WebRequest): RestErrorResponse {
        return RestErrorResponse("", DefaultError(ex.message))

    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/blogs")
    fun addBlog(@Valid blogInputForm: BlogInputForm, bindingResult: BindingResult) {

        if (bindingResult.allErrors.size > 0) {
            val errorFields = bindingResult.fieldErrors

            val errorList = errorFields.map {

                return@map (it.field + ": " + it.defaultMessage ?: "")
            }
            val errorString = errorList.stream().collect(Collectors.joining(" \n "))


            throw RuntimeException(errorString)
            /*   throw ResponseStatusException(
                       HttpStatus.NOT_FOUND, errorString)
           }*/

        }


        @PreAuthorize("hasRole('USER')")
        @PostMapping("/blogs/search")
        fun searchBlog(blogForm: BlogSearchForm): List<GetBlogListResponse> {
            val sort = Sort.by(Sort.Direction.DESC, "updateTime")
            val blogs = blogService.listBlog(blogForm, sort)
            val result = blogs.map {
                GetBlogListResponse(it.id, it.title, it.recommend, it.published, it.updateTime)
            }
            return result
        }

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
}