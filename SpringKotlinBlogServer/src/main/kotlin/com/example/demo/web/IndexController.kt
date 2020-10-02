package com.example.demo.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class IndexController {

    @GetMapping("/")
    fun index(): String {


        return "admin/types"
    }

    @GetMapping("/try")
    fun index2(): String {


        return "admin/types-input"
    }
}
