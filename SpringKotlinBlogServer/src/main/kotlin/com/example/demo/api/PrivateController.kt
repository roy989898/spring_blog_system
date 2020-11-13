package com.example.demo.api

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping

import org.springframework.web.bind.annotation.RequestMapping

import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping()
class PrivateController {


    @GetMapping("/api/private")
    fun private(): String {
//        get current user information
//        val up = SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken
        return "Hello from private API controller"
    }


    @GetMapping("/api/other")
    fun other(): String {
//        get current user information
//        val up = SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken
        return "other"
    }

}