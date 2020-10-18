package com.example.demo.Interceptor

import com.example.demo.service.BlogService
import com.example.demo.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class BlogDisplayInterceptor(val userService: UserService, val blogService: BlogService) : HandlerInterceptor {
    private val LOGGER = LoggerFactory.getLogger(BlogDisplayInterceptor::class.java)


    override fun postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any, modelAndView: ModelAndView?) {
       val url= request.requestURI
        val user = userService.listUser().run {
            if (this.isEmpty()) {
                null
            } else {
                this[0]
            }
        }
        val blogs = blogService.listNewestBlogTop(3)
        modelAndView?.addObject("user", user)
        modelAndView?.addObject("blogs", blogs)
        super.postHandle(request, response, handler, modelAndView)
    }
}