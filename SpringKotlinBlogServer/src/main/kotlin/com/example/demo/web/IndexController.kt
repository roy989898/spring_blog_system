package com.example.demo.web

import com.example.demo.NotFoundException
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class IndexController {

    @GetMapping("/")
    fun index(): String {

       /* val blog = null
        if (blog == null) {
            throw NotFoundException("Blog not found")
        }*/
        return "index"
    }
}