package com.example.demo.web.admin

import com.example.demo.form.BlogSearchForm
import com.example.demo.po.Type
import com.example.demo.service.BlogService
import com.example.demo.service.TypeService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/admin")
class BlogController(val blogService: BlogService, val typeService: TypeService) {


    @GetMapping("/blogs", "/blogs/{page_num}")
    fun blogs(model: Model, page_num: Int?, blogSearchForm: BlogSearchForm): String {
        val usedPageNUm = page_num ?: 0
        val sort = Sort.by(Sort.Direction.ASC, "id")
        val pb = PageRequest.of(usedPageNUm, 10, sort)


        val blogs = blogService.listBlog(pb, blogSearchForm)
        val types = typeService.listType().toMutableList()
        val emptyType = Type(null, "", null)
        types.add(0, emptyType)
        model.addAttribute("page", blogs)
        model.addAttribute("types", types)
        return "admin/blogs"

    }


    @PostMapping("/blogs/search")
    fun search(model: Model, page_num: Int?, blogSearchForm: BlogSearchForm): String {
        val usedPageNUm = page_num ?: 0
        val sort = Sort.by(Sort.Direction.ASC, "id")
        val pb = PageRequest.of(usedPageNUm, 10, sort)


        val blogs = blogService.listBlog(pb, blogSearchForm)
        model.addAttribute("page", blogs)
        return "admin/blogs::blogList"

    }
}