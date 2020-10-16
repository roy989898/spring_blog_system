package com.example.demo.web.admin

import com.example.demo.NotFoundException
import com.example.demo.form.AboutMeInputForm
import com.example.demo.getSessionUser
import com.example.demo.saveSessionUser
import com.example.demo.service.UserService
import com.example.demo.unwrap
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpSession

@Controller
@RequestMapping("/admin")
class AboutMeController(val userService: UserService) {

    @GetMapping("/aboutMeAdmin")
    fun aboutMe(): String {

        return "admin/about_me_input"

    }


    @PostMapping("/aboutMeAdmin")
    fun aboutMeInput(aboutMeInputForm: AboutMeInputForm, httpSession: HttpSession): String {

        val sessionUser = httpSession.getSessionUser()
        val dbUser = userService.getUser(sessionUser?.id ?: -1).unwrap()
        if (dbUser != null) {
            val nickName = aboutMeInputForm.nickName
            val email = aboutMeInputForm.email
            val phone = aboutMeInputForm.phone
            val aboutMe = aboutMeInputForm.aboutMe
            val hiddenPictureInput = aboutMeInputForm.hiddenPictureInput
            val hiddenIconInput = aboutMeInputForm.hiddenIconInput
            nickName?.let {
                dbUser.nickname = it
            }
            email?.let {
                dbUser.email = it
            }
            phone?.let {
                dbUser.phone = it
            }
            aboutMe?.let {
                dbUser.aboutMe = it
            }
            hiddenPictureInput?.let {
                dbUser.picture = it
            }
            hiddenIconInput?.let {
                dbUser.avatar = it
            }
            userService.saveUser(dbUser)
            dbUser.password = ""
            httpSession.saveSessionUser(dbUser)

            return "redirect:/admin/aboutMeAdmin"
        } else {
            throw NotFoundException("User not found")
        }


    }


}