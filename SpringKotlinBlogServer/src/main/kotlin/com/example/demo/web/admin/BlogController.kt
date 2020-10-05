package com.example.demo.web.admin

import com.example.demo.form.BlogInputForm
import com.example.demo.form.BlogSearchForm
import com.example.demo.getSessionUser
import com.example.demo.po.Type
import com.example.demo.service.BlogService
import com.example.demo.service.TypeService
import com.example.demo.service.UserService
import com.example.demo.unwrap
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.servlet.http.HttpSession
import javax.transaction.Transactional
import javax.validation.Valid

@Controller
@RequestMapping("/admin")
class BlogController(val blogService: BlogService, val typeService: TypeService, val userService: UserService) {

    companion object {
//       const val link = "foo"
    }

    @GetMapping("/blogs", "/blogs/{page_num}")
    fun blogs(model: Model, page_num: Int?, blogSearchForm: BlogSearchForm): String {
        val usedPageNUm = page_num ?: 0
        val sort = Sort.by(Sort.Direction.ASC, "id")
        val pb = PageRequest.of(usedPageNUm, 10, sort)


        val blogs = blogService.listBlog(pb, blogSearchForm)
        val types = typeService.listType().toMutableList()
        val emptyType = Type(null, "", null)
        types.add(0, emptyType)
        model.addAttribute("page", blogs)
        model.addAttribute("types", types)
        return "admin/blogs"

    }

    //    @Transactional
    @PostMapping("/blogs")
    fun blogInput(@Valid blogInputForm: BlogInputForm, bindingResult: BindingResult, redirectAttributes: RedirectAttributes, model: Model, httpSession: HttpSession): String {


        return if (bindingResult.allErrors.size > 0) {
            val errorFields = bindingResult.fieldErrors
            val errors = HashMap<String, String>()
            errorFields.forEach {
                errors[it.field] = it.defaultMessage ?: ""
            }
//            model.addAttribute("errors", errors)
            redirectAttributes.addFlashAttribute("errors", errors)
            "redirect:/admin/blogs/input"
        } else {
//            TODO save
            val userInSession = httpSession.getSessionUser()
        /*    val dbUser = userInSession?.id?.let {
                return@let userService.getUser(it).unwrap()
            }*/

            if (userInSession != null) {

                val newBlog = blogInputForm.toBlog()

                newBlog.user = userInSession
                blogService.saveBlog(newBlog)

                "redirect:/admin/blogs"
            } else {
                "redirect:/admin/blogs/input"
            }


        }


    }

    @GetMapping("/blogs/input")
    fun blogCreate(model: Model): String {

        val types = typeService.listType().toMutableList()


        model.addAttribute("types", types)

        return "admin/blogs_input"
    }


    @PostMapping("/blogs/search")
    fun search(model: Model, blogSearchForm: BlogSearchForm): String {
        val usedPageNUm = 0
        val sort = Sort.by(Sort.Direction.ASC, "id")
        val pb = PageRequest.of(usedPageNUm, 10, sort)


        val blogs = blogService.listBlog(pb, blogSearchForm)
        model.addAttribute("page", blogs)
        return "admin/blogs::blogList"

    }

    @PostMapping("/blogs/searchButton")
    fun searchButtonPart(model: Model, blogSearchForm: BlogSearchForm): String {
        val usedPageNUm = 0
        val sort = Sort.by(Sort.Direction.ASC, "id")
        val pb = PageRequest.of(usedPageNUm, 10, sort)


        val blogs = blogService.listBlog(pb, blogSearchForm)
        model.addAttribute("page", blogs)
        return "admin/blogs::blogButton"

    }
}