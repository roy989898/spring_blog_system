package com.example.demo.web.admin

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AboutMeController {

    @GetMapping("/aboutMeAdmin")
    fun aboutMe(): String {

        return "admin/about_me_input"

    }


}