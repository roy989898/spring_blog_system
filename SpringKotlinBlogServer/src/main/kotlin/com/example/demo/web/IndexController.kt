package com.example.demo.web

import com.example.demo.form.BlogSearchForm
import com.example.demo.service.BlogService
import com.example.demo.service.TagService
import com.example.demo.service.TypeService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class IndexController(val blogService: BlogService, val tagService: TagService, val typeService: TypeService) {

    @GetMapping("/", "/{page_num}")
    fun index(model: Model, @PathVariable(required = false) page_num: Int?): String {
        val usedPageNUm = page_num ?: 0
        val sort = Sort.by(Sort.Direction.ASC, "id")
        val pb = PageRequest.of(usedPageNUm, 10, sort)
        val page = blogService.listBlog(pb)
        val types = typeService.listTypeTop(6)
        val tags = tagService.getTagsTop(6)
        val recommendedBlogs = blogService.listBlogTop(8)
        model.addAttribute("page", page)
        model.addAttribute("blogs", page.content)
        model.addAttribute("types", types)
        model.addAttribute("tags", tags)
        model.addAttribute("recommendedBlogs", recommendedBlogs)
        return "index"
    }

    @GetMapping("/try")
    fun index2(): String {


        return "search"
    }
}
