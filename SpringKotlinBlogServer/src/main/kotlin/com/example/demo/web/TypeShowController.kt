package com.example.demo.web

import com.example.demo.po.Type
import com.example.demo.service.BlogService
import com.example.demo.service.TagService
import com.example.demo.service.TypeService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class TypeShowController(val blogService: BlogService, val typeService: TypeService) {


    @GetMapping("/types", "/types/{typeId}")
    fun types(@PathVariable typeId: Long?, model: Model): String {
        val types = typeService.listType()
        var type: Type? = null
        if (typeId != null) {
            type = typeService.getType(typeId)
        } else {

            if (types.size > 0) {
                type = types[0]
            }
        }

//        TODO blog list
        val blogs = type?.blogs ?: emptyList()

        model.addAttribute("types", types)
        model.addAttribute("blogs", blogs)
        model.addAttribute("typeId", type?.id)



        return "category"
    }
}