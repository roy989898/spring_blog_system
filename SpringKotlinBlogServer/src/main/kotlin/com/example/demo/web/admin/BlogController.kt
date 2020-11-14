package com.example.demo.web.admin

import com.example.demo.errorHandle.NotFoundException
import com.example.demo.currentUser
import com.example.demo.form.BlogInputForm
import com.example.demo.form.BlogSearchForm
import com.example.demo.getSessionUser
import com.example.demo.po.Tag
import com.example.demo.po.Type
import com.example.demo.service.BlogService
import com.example.demo.service.TagService
import com.example.demo.service.TypeService
import com.example.demo.service.UserService
import com.example.demo.unwrap
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.servlet.http.HttpSession
import javax.transaction.Transactional
import javax.validation.Valid

@Controller
@RequestMapping("/admin")
class BlogController(val tagService: TagService, val blogService: BlogService, val typeService: TypeService, val userService: UserService) {
    private val LOGGER = LoggerFactory.getLogger(BlogController::class.java)

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
    fun blogInput(@Valid blogInputForm: BlogInputForm, bindingResult: BindingResult, redirectAttributes: RedirectAttributes, model: Model, httpSession: HttpSession, authentication: Authentication): String {


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
            val userInSession = authentication.currentUser()
            /*    val dbUser = userInSession?.id?.let {
                    return@let userService.getUser(it).unwrap()
                }*/
            val type = blogInputForm.typeId?.let {
                typeService.getType(it)
            }

            if (userInSession != null) {

                val newBlog = blogInputForm.toBlog()
                val tagStringArray = blogInputForm.tags?.split('_')?.toHashSet()?.toList() ?: emptyList()
                val tagList = tagStringArray.map {
                    return@map Tag(null, it, emptyList())
                }

                val vTags = tagList.map {
                    val gotTag = tagService.getTag(it.name).unwrap()

                    return@map gotTag ?: it
                }
//
                newBlog.tags = vTags

                newBlog.user = userInSession
                type?.let {
                    newBlog.type = it
                }
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

    @GetMapping("/blogs/{blogId}/input")
    fun blogEdit(@PathVariable blogId: Long, model: Model): String {

        val types = typeService.listType().toMutableList()

        val blog = blogService.getBlog(blogId)
        if (blog != null) {
            model.addAttribute("types", types)
            model.addAttribute("blog", blog)

//            LOGGER.info(blog.getTagsString())
            return "admin/blogs_input"
        } else {
            throw NotFoundException("Blog not found")
        }


    }

    @GetMapping("/blogs/{blogId}/delete")
    fun blogDelete(@PathVariable blogId: Long, model: Model, httpSession: HttpSession, authentication: Authentication): String {

        val blog = blogService.getBlog(blogId)
        blog?.let {
            if (authentication.currentUser()?.id == it.user?.id) {
                blogService.deleteBlog(blogId)
            }

        }

        return "redirect:/admin/blogs"
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