package com.example.demo

import com.example.demo.po.User
import com.example.demo.utility.MD5
import java.util.*
import javax.servlet.http.HttpSession

fun <T> Optional<T>.unwrap(): T? = orElse(null)


fun HttpSession.getSessionUser(): User? {
    return this.getAttribute("user") as User?
}

fun HttpSession.saveSessionUser(user: User) {
    this.setAttribute("user", user)
}

fun HttpSession.removeSessionUser() {
    this.removeAttribute("user")
}

fun String.toMD5(): String? {
    return MD5.getMd5(this)
}