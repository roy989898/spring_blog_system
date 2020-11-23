package com.example.demo.api

import com.example.demo.service.BlogService
import com.example.demo.service.TagService
import com.example.demo.service.TypeService
import com.example.demo.service.UserService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("/api/client")
class ClientApiController(private val blogService: BlogService,
                          private val tagService: TagService, private val userService: UserService,
                          private val typeService: TypeService) {






}