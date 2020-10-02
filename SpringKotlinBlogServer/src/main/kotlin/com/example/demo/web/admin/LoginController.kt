package com.example.demo.web.admin

import com.example.demo.removeSessionUser
import com.example.demo.saveSessionUser
import com.example.demo.service.UserService
import com.example.demo.toMD5
import com.example.demo.unwrap
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.servlet.http.HttpSession

@Controller
@RequestMapping("/admin")
class LoginController(val userService: UserService) {


    @GetMapping
    fun loginPage(): String {
        return "admin/login"

    }


    @PostMapping("/login")
    fun login(@RequestParam username: String, @RequestParam password: String, session: HttpSession,
              redirectAttributes: RedirectAttributes): String {

        val user = userService.checkUser(username, password).unwrap()
        return if (user != null) {
            user.password = ""
            session.saveSessionUser(user)

            "redirect:/admin/blogs"
        } else {
            redirectAttributes.addFlashAttribute("message", "username or password not correct")

            "redirect:/admin"

        }

    }

    @GetMapping("/logout")
    fun logout(session: HttpSession): String {
        session.removeSessionUser()

        return "redirect:/admin"
    }
}