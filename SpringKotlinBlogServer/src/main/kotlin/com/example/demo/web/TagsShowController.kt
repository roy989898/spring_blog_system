package com.example.demo.web

import com.example.demo.po.Tag
import com.example.demo.po.Type
import com.example.demo.service.TagService
import com.example.demo.unwrap
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class TagsShowController(val tagService: TagService) {

    @GetMapping("/tags", "/tags/{tagID}")
    fun tags(@PathVariable tagID: Long?, model: Model): String {
        val tags = tagService.getTag()
        var tag: Tag? = null
        var usedTagId: Long? = null
        if (tagID != null) {
            usedTagId = tagID
            tag = tagService.getTag(tagID).unwrap()
        } else {


            if (tags.isNotEmpty()) {
                tag = tags[0]
                usedTagId = tag.id
            }
        }


        val blogs = tag?.blogs ?: emptyList()

        model.addAttribute("tags", tags)
        model.addAttribute("blogs", blogs)
        model.addAttribute("tagID", usedTagId)

        return "tags"
    }
}