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
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

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


    @GetMapping("/search", "/search/{page_num}")
    fun search(@RequestParam query: String?, @PathVariable(required = false) page_num: Int?, model: Model): String {
        val key = query ?: ""
        val usedPageNUm = page_num ?: 0
        val sort = Sort.by(Sort.Direction.DESC, "updateTime")
        val pb = PageRequest.of(usedPageNUm, 10, sort)
        val page = blogService.listBlog(key, pb)
        model.addAttribute("page", page)
        model.addAttribute("blogs", page.content)
        model.addAttribute("key", key)
        return "search"
    }

    @GetMapping("/try")
    fun index2(): String {


        return "search"
    }
}
