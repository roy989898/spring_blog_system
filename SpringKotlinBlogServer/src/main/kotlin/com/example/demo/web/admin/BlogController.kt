package com.example.demo.web.admin

import com.example.demo.form.BlogSearchForm
import com.example.demo.service.BlogService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/admin")
class BlogController(val blogService: BlogService) {


    @GetMapping("/blogs", "/blogs/{page_num}")
    fun blogs(model: Model, page_num: Int?, blogSearchForm: BlogSearchForm): String {
        val usedPageNUm = page_num ?: 0
        val sort = Sort.by(Sort.Direction.ASC, "id")
        val pb = PageRequest.of(usedPageNUm, 10, sort)


        val blogs = blogService.listBlog(pb, blogSearchForm)
        model.addAttribute("page", blogs)
        return "admin/blogs"

    }
}