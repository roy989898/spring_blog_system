package com.example.demo

import com.example.demo.po.User
import com.example.demo.utility.MD5
import org.springframework.security.core.Authentication
import org.springframework.validation.BindingResult
import java.util.*
import java.util.stream.Collectors
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

fun Authentication.currentUser(): User? {
    return this.principal as User?
}

fun String.toMD5(): String? {
    return MD5.getMd5(this)
}


fun BindingResult.toRestErrorString(): String {
    val errorFields = this.fieldErrors

    val errorList = errorFields.map {

        return@map (it.field + ": " + it.defaultMessage ?: "")
    }
    val errorString = errorList.stream().collect(Collectors.joining(" \n "))

    return errorString
}


fun String.getBrief(): String {
    return if (this.length <= 50) {
        this
    } else {
        this.substring(50)
    }
}