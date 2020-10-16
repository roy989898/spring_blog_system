package com.example.demo.web.admin

import com.example.demo.form.AboutMeInputForm
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/admin")
class AboutMeController {

    @GetMapping("/aboutMeAdmin")
    fun aboutMe(): String {

        return "admin/about_me_input"

    }


    @PostMapping("/aboutMeAdmin")
    fun aboutMeInput(aboutMeInputForm: AboutMeInputForm): String {
//        TODO

        return "admin/about_me_input"

    }


}