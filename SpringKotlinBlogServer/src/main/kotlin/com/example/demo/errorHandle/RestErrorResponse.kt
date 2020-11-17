package com.example.demo.errorHandle

import com.example.demo.errorHandle.RestAPi.DefaultError
import com.google.gson.Gson
import java.io.PrintWriter
import javax.servlet.http.HttpServletResponse

data class RestErrorResponse(val message: String, val error: DefaultError?) {


/*    fun addErrorToResponseAJson(httpServletResponse: HttpServletResponse) {
        val gson = Gson()
        val json = gson.toJson(this)
        val out: PrintWriter = httpServletResponse.getWriter()
        httpServletResponse.contentType = "application/json"
        httpServletResponse.characterEncoding = "UTF-8"
        out.print(json)
        out.flush()
    }*/
}