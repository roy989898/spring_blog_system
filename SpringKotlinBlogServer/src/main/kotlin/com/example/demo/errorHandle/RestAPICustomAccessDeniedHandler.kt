package com.example.demo.errorHandle

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class RestAPICustomAccessDeniedHandler : AccessDeniedHandler {
    override fun handle(request: HttpServletRequest, response: HttpServletResponse, exc: AccessDeniedException) {
        print("RestAPICustomAccessDeniedHandler")
    }
}