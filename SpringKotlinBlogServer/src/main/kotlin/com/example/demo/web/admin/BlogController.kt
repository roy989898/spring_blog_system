package com.example.demo.web.admin

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/admin")
class BlogController {


    @GetMapping("/blogs")
    fun blogs(): String {
        return "admin/blogs"

    }
}