package com.example.demo.service

import com.example.demo.dao.UserRepository
import com.example.demo.po.User
import com.example.demo.toMD5
import org.springframework.stereotype.Service
import java.util.*


@Service
class UserServiceImp(val userRepository: UserRepository) : UserService {
    override fun checkUser(username: String, password: String): Optional<User> {
        val user = userRepository.findByUsernameAndPassword(username, password.toMD5() ?: "")
        return user
    }

    override fun getUser(id: Long): Optional<User> {
        return userRepository.findById(id)
    }
}