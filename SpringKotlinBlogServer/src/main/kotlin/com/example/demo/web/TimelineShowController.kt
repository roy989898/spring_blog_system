package com.example.demo.web

import com.example.demo.po.Blog
import com.example.demo.service.BlogService
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.text.SimpleDateFormat

@Controller
class TimelineShowController(val blogService: BlogService) {


    @GetMapping("/timeline")
    fun timeLine(model: Model): String {
        val sort = Sort.by(Sort.Direction.ASC, "createTime")
        val blogs = blogService.listBlog(sort)
        val simpleDateFormat = SimpleDateFormat("YYYY")
        val yearBlogList = blogs.map {
            val year = simpleDateFormat.format(it.createTime)
            YearAndBlog(year, it)
        }
        val yearBlogsHm = HashMap<String, Array<Blog>>()
        yearBlogList.forEach {


            val blogArray = yearBlogsHm[it.year] ?: emptyArray()
            val newBlogArray = blogArray.plus(it.blog)

            yearBlogsHm[it.year] = newBlogArray

        }

        val keys = yearBlogsHm.keys
        val yearAndBlogSList = keys.map { key ->

            val blogs = yearBlogsHm[key] ?: emptyArray()
            YearAndBlogS(key, blogs)
        }

        model.addAttribute("yearAndBlogSList", yearAndBlogSList)
        model.addAttribute("blogs", blogs)

        return "timeline"
    }


    data class YearAndBlog(val year: String, val blog: Blog)
    data class YearAndBlogS(val year: String, val blogs: Array<Blog>)
}