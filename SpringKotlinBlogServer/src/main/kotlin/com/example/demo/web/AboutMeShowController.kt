package com.example.demo.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AboutMeShowController {

    @GetMapping("/aboutMe")
    fun aboutMe(): String {

        return "about_me"

    }
}