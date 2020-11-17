package com.example.demo.errorHandle.RestAPi

import com.example.demo.errorHandle.RestErrorResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest


@RestController
@RequestMapping(SuperHeroErrorController.ERROR_PATH)
class SuperHeroErrorController(errorAttributes: ErrorAttributes?) : AbstractErrorController(errorAttributes, emptyList()) {
    @RequestMapping
    fun error(request: HttpServletRequest?): ResponseEntity<Map<String, Any>> {
//         TODO handle the rest api here!!!
        val body = this.getErrorAttributes(request, false)
        val gson = Gson()
        val jsonTree = gson.toJsonTree(body)
        val defaultError = gson.fromJson(jsonTree, DefaultError::class.java)
        val rer = RestErrorResponse("myMessage", defaultError)
        val status = getStatus(request)
        val turnsType = object : TypeToken<HashMap<String, Any>>() {}.type
        val newBody = gson.fromJson<HashMap<String, Any>>(gson.toJson(rer), turnsType)
        return ResponseEntity(newBody, status)
    }

    override fun getErrorPath(): String {
        return ERROR_PATH
    }

    companion object {
        const val ERROR_PATH = "/error"
    }
}