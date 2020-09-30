package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringKotlinBlogServerApplication

fun main(args: Array<String>) {
    runApplication<SpringKotlinBlogServerApplication>(*args)
}
