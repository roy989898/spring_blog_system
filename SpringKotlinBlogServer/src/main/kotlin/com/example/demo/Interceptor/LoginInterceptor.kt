package com.example.demo.Interceptor

import com.example.demo.getSessionUser
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class LoginInterceptor : HandlerInterceptor {
    private val LOGGER = LoggerFactory.getLogger(LoginInterceptor::class.java)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

        return if (request.session.getSessionUser() == null) {
            response.sendRedirect("/admin")
            false
        } else

            super.preHandle(request, response, handler)
    }

    override fun postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any, modelAndView: ModelAndView?) {
        val userInSession = request.session.getSessionUser()
        modelAndView?.addObject("user", userInSession)
        super.postHandle(request, response, handler, modelAndView)
    }
}