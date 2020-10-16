package com.example.demo.web

import com.example.demo.service.TagService
import com.example.demo.service.TypeService
import com.example.demo.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AboutMeShowController(val userService: UserService, val typeService: TypeService, val tagService: TagService) {

    @GetMapping("/aboutMe")
    fun aboutMe(model: Model): String {
        val user = userService.listUser().run {
            if (this.isEmpty()) {
                null
            } else {
                this[0]
            }
        }
        val types = typeService.listType()
        val tags = tagService.listTag()
        model.addAttribute("user", user)
        model.addAttribute("types", types)
        model.addAttribute("tags", tags)
        return "about_me"

    }
}