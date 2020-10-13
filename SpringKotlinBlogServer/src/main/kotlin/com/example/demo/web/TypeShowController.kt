package com.example.demo.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class TypeShowController {


    @GetMapping("/types", "/types/{typeId}")
    fun types(@PathVariable typeId: Long?): String {
        return "category"
    }
}