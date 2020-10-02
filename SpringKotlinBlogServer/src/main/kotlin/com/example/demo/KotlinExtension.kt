package com.example.demo

import com.example.demo.po.User
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