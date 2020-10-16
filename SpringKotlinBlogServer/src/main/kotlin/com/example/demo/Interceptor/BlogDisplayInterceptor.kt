package com.example.demo.Interceptor

import org.slf4j.LoggerFactory
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class BlogDisplayInterceptor() : HandlerInterceptor {
    private val LOGGER = LoggerFactory.getLogger(BlogDisplayInterceptor::class.java)


    override fun postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any, modelAndView: ModelAndView?) {
        super.postHandle(request, response, handler, modelAndView)
    }
}