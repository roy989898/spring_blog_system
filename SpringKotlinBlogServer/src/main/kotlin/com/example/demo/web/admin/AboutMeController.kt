package com.example.demo.web.admin

import com.example.demo.form.AboutMeInputForm
import com.example.demo.getSessionUser
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpSession

@Controller
@RequestMapping("/admin")
class AboutMeController {

    @GetMapping("/aboutMeAdmin")
    fun aboutMe(): String {

        return "admin/about_me_input"

    }


    @PostMapping("/aboutMeAdmin")
    fun aboutMeInput(aboutMeInputForm: AboutMeInputForm, httpSession: HttpSession): String {
//        TODO
        httpSession.getSessionUser()

        return "admin/about_me_input"

    }


}