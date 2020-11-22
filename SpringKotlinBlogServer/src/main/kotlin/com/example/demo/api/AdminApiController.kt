package com.example.demo.api

import com.example.demo.api.Response.GetBlogListResponse
import com.example.demo.api.Response.RestAboutMeResponse
import com.example.demo.api.Response.RestBlogDetailResponse
import com.example.demo.api.Response.RestTypeListResponse
import com.example.demo.currentUser
import com.example.demo.errorHandle.NotFoundException
import com.example.demo.errorHandle.RestAPi.DefaultError
import com.example.demo.errorHandle.RestErrorResponse
import com.example.demo.form.AboutMeInputForm
import com.example.demo.form.BlogInputForm
import com.example.demo.form.BlogSearchForm
import com.example.demo.po.Tag
import com.example.demo.po.Type
import com.example.demo.saveSessionUser
import com.example.demo.service.BlogService
import com.example.demo.service.TagService
import com.example.demo.service.TypeService
import com.example.demo.service.UserService
import com.example.demo.toRestErrorString
import com.example.demo.unwrap
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.lang.RuntimeException
import javax.servlet.http.HttpSession
import javax.validation.Valid


@RestController()
@RequestMapping("/api/admin")
class AdminApiController(private val blogService: BlogService, private val tagService: TagService, private val userService: UserService, private val typeService: TypeService) {

    private val LOGGER = LoggerFactory.getLogger(AdminApiController::class.java)


    private fun getUser(): UsernamePasswordAuthenticationToken {
        val up = SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken
        return up
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleError(ex: Exception, request: WebRequest): RestErrorResponse {
        return RestErrorResponse("", DefaultError(ex.message))

    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/blogs/{id}")
    fun getBlogDetail(@PathVariable id: Long): RestBlogDetailResponse {
        val blog = blogService.getBlog(id)


        if (blog != null) {
            val comment = blog.comments.map {
                it.removeAllRelation()
                return@map it
            }

            val tag = blog.tags.map {
                it.blogs = emptyList()
                return@map it
            }

            return RestBlogDetailResponse(
                    blog.id, blog.title, blog.content, blog.firstPicture, blog.flag, blog.vies, blog.appreciation, blog.shareStatement, blog.commentabled, blog.published, blog.recommend, blog.createTime, blog.updateTime,
                    blog.type?.removeAllRelation(), comment, tag.toMutableSet()
            )

        } else {
            throw NotFoundException("blog not found")
        }


        TODO()
    }


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/blogs")
    fun addBlog(@Valid blogInputForm: BlogInputForm, bindingResult: BindingResult) {

        if (bindingResult.allErrors.size > 0) {

            val errorString = bindingResult.toRestErrorString()


            throw RuntimeException(errorString)
            /*   throw ResponseStatusException(
                       HttpStatus.NOT_FOUND, errorString)
           }*/

        }

        val user = getUser()
        val dbUser = userService.getUser(user.name).unwrap()
        if (dbUser == null) {
            throw RuntimeException("User not found")
        } else {
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
            newBlog.tags = vTags.toMutableSet()
            newBlog.user = dbUser
            val type = blogInputForm.typeId?.let {
                typeService.getType(it)
            }
            type?.let {
                newBlog.type = it
            }
            blogService.saveBlog(newBlog)
        }


    }


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/blogs/search")
    fun searchBlog(blogForm: BlogSearchForm): List<GetBlogListResponse> {
        val sort = Sort.by(Sort.Direction.DESC, "updateTime")
        val blogs = blogService.listBlog(blogForm, sort)
        val result = blogs.map {
            GetBlogListResponse(it.id, it.title, it.recommend, it.published, it.updateTime)
        }
        return result
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/blogs")
    fun getBlogList(): List<GetBlogListResponse> {
        val sort = Sort.by(Sort.Direction.DESC, "updateTime")
        val blogs = blogService.listBlog(sort)
        val result = blogs.map {
            GetBlogListResponse(it.id, it.title, it.recommend, it.published, it.updateTime)
        }
        return result
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/category")
    fun getCategory(): List<RestTypeListResponse> {
//        val sort = Sort.by(Sort.Direction.DESC, "updateTime")
        val types = typeService.listType().map {
            RestTypeListResponse(it.id, it.name)
        }

        return types
    }


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/category")
    fun addSaveCategory(@Valid type: Type, bindingResult: BindingResult, redirectAttributes: RedirectAttributes) {
//        val sort = Sort.by(Sort.Direction.DESC, "updateTime")
        val findType = typeService.getTypeNyName(type.name)
        if (findType == null) {
            if (bindingResult.allErrors.size > 0) {

                val errorString = bindingResult.toRestErrorString()
                throw RuntimeException(errorString)


            } else {
                val aType = Type(type.id, type.name, emptyList())
                typeService.save(aType)


            }
        }

    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/category/{id}/delete")
    @Transactional
    fun deleteCategory(@PathVariable id: Long) {
        val types = typeService.getType(id)
        types?.let {
            val blogs = it.blogs ?: emptyList()
            val ids = blogs.map {
                return@map it.id ?: 0
            }
            blogService.removeBlogTypeByBlogIds(ids)
        }
        typeService.deleteType(id)

    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tag")
    fun getTags(@PathVariable id: Long): List<RestTypeListResponse> {
        val result = tagService.listTag().map {
            RestTypeListResponse(it.id, it.name)
        }

        return result
    }


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/tag")
    fun addTag(@Valid tag: Tag, bindingResult: BindingResult, redirectAttributes: RedirectAttributes) {
        val findTag = tagService.getTag(tag.name).unwrap()
        if (findTag == null) {
            if (bindingResult.allErrors.size > 0) {

                val errorString = bindingResult.toRestErrorString()
                throw RuntimeException(errorString)


            } else {
                val aType = Type(tag.id, tag.name, emptyList())
                typeService.save(aType)


            }
        }


    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tag/{id}/delete")
    @Transactional
    fun deleteTag(@PathVariable id: Long) {
        val tag = tagService.getTag(id).unwrap()
        tag?.let {
            val blogs = it.blogs
            val ids = blogs.map {
                return@map it.id ?: 0
            }
            blogService.removeBlogTagByBlogIds(ids, it.id)
            tagService.deleteTagBuId(id)
        }


    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/aboutMe")
    fun aboutMeInput(aboutMeInputForm: AboutMeInputForm) {

        val user = getUser()
        val dbUser = userService.getUser(user.name).unwrap()
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


        } else {
            throw NotFoundException("User not found")
        }


    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/aboutMe")
    fun aboutMe(): RestAboutMeResponse {

        val user = getUser()
        val dbUser = userService.getUser(user.name).unwrap()
        if (dbUser != null) {


            return RestAboutMeResponse(dbUser.nickname, dbUser.email, dbUser.phone, dbUser.aboutMe, dbUser.picture, dbUser.avatar)


        } else {
            throw NotFoundException("User not found")
        }


    }


}
