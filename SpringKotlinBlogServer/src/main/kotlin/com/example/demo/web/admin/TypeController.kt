package com.example.demo.web.admin

import com.example.demo.Interceptor.StaticResourceInterceptor
import com.example.demo.po.Type
import com.example.demo.service.TypeService
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/admin")
class TypeController(val typeService: TypeService) {
    private val LOGGER = LoggerFactory.getLogger(TypeController::class.java)

    @GetMapping("/types", "/types/{page_num}")
    fun list(model: Model, @PathVariable(required = false) page_num: Int?): String {
        val usedPageNUm = page_num ?: 0
        val sort = Sort.by(Sort.Direction.DESC, "id")
        val pb = PageRequest.of(usedPageNUm, 10, sort)

        val types = typeService.listType(pb)
        LOGGER.info(types.isFirst.toString())
        LOGGER.info(types.isLast.toString())
        model.addAttribute("types", types)
        return "admin/types"

    }

    @GetMapping("/types/input")
    fun input(): String {
        return "admin/types_input"
    }

    @PostMapping("/types")
    fun saveType(@RequestParam type: String, redirectAttributes: RedirectAttributes): String {
//        LOGGER.info(type)

        val findType = typeService.getTypeNyName(type)

        if (findType == null) {
            val aType = Type(null, type, emptyList())
            typeService.save(aType)

            return "redirect:/admin/types"
        } else {
//            TODO
            redirectAttributes.addFlashAttribute("message", "already exist")
            return "redirect:/admin/types/input"
        }


    }


}