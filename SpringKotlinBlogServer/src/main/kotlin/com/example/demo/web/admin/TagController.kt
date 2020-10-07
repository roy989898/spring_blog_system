package com.example.demo.web.admin

import com.example.demo.po.Tag
import com.example.demo.service.TagService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/admin")
class TagController(private val tagService: TagService) {


    @GetMapping("/tags/{name}")
    @ResponseBody
    fun getTagsByName(@PathVariable name: String): ArrayList<Tag> {
        val tags = tagService.getTag(name)

        return tags

    }




}