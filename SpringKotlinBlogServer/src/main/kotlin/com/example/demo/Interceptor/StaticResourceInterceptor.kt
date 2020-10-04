package com.example.demo.Interceptor

import com.example.demo.web.ControllerExceptionHandler
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class StaticResourceInterceptor : HandlerInterceptor {
    //    http://localhost:8080/static/lib/editormd/lib/codemirror/addon/dialog/dialog.css
//    http://localhost:8080/lib/editormd/lib/codemirror/codemirror.min.css
    private val LOGGER = LoggerFactory.getLogger(StaticResourceInterceptor::class.java)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val url = getFullURL(request)

//        LOGGER.warn(url)
        if (url.contains("static/lib/")) {
//            val newUrl = url.replace("static/", "")
            val indexOfLibString = url.indexOf("static/") + "static/".length
            val subString = url.substring(indexOfLibString, url.length)
            val newUrl = request.getScheme() + "://" +   // "http" + "://
                    request.getServerName() +       // "myhost"
                    ":" + request.getServerPort() + "/" + subString // ":" + "8080"
            response.sendRedirect(newUrl)
            return false
        }

//        LOGGER.warn(newUrl)
        return true
    }

    private fun getFullURL(request: HttpServletRequest): String {
        val requestURL = StringBuilder(request.requestURL.toString())
        val queryString = request.queryString
        return if (queryString == null) {
            requestURL.toString()
        } else {
            requestURL.append('?').append(queryString).toString()
        }
    }
}