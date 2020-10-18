package com.example.demo.service

import com.example.demo.po.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserService {
    fun checkUser(username: String, password: String): Optional<User>

    fun getUser(id: Long): Optional<User>
    fun getUser(name: String): Optional<User>

    fun listUser(): List<User>

    fun saveUser(user: User)
}