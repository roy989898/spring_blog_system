package com.example.demo.web.admin

import com.example.demo.Interceptor.StaticResourceInterceptor
import com.example.demo.po.Type
import com.example.demo.service.BlogService
import com.example.demo.service.TypeService
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.validation.Valid

@Controller
@RequestMapping("/admin")
class TypeController(val typeService: TypeService, val blogService: BlogService) {
    private val LOGGER = LoggerFactory.getLogger(TypeController::class.java)

    @GetMapping("/types", "/types/{page_num}")
    fun list(model: Model, @PathVariable(required = false) page_num: Int?): String {
        val usedPageNUm = page_num ?: 0
        val sort = Sort.by(Sort.Direction.DESC, "id")
        val pb = PageRequest.of(usedPageNUm, 2, sort)

        val types = typeService.listType(pb)
/*        LOGGER.info(types.isFirst.toString())
        LOGGER.info(types.isLast.toString())*/
        model.addAttribute("types", types)
        return "admin/types"

    }

    @GetMapping("/types/input")
    fun input(): String {
        return "admin/types_input"
    }


    @GetMapping("/types/{id}/input")
    fun editInput(@PathVariable id: Long, model: Model): String {
        val type = typeService.getType(id)
        model.addAttribute("type", type)
        return "admin/types_input"
    }


    @GetMapping("/types/{id}/delete")
    fun delete(@PathVariable id: Long, model: Model): String {

        typeService.deleteType(id)
        return "redirect:/admin/types"
    }

    @PostMapping("/types")
    fun saveType(@Valid type: Type, bindingResult: BindingResult, redirectAttributes: RedirectAttributes): String {
//        LOGGER.info(type)

        val findType = typeService.getTypeNyName(type.name)

        if (findType != null) {
            bindingResult.addError(ObjectError("exist", "already exist"))
        }

        if (bindingResult.allErrors.size > 0) {
            //            TODO
            val errors = bindingResult.allErrors
            redirectAttributes.addFlashAttribute("message", errors[0].defaultMessage)
            return "redirect:/admin/types/input"


        } else {
            val aType = Type(type.id, type.name, emptyList())
            typeService.save(aType)

            return "redirect:/admin/types"
        }


    }


}