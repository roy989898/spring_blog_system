package com.example.demo.service

import com.example.demo.dao.UserRepository
import com.example.demo.po.User
import org.springframework.stereotype.Service
import java.util.*


@Service
class UserServiceImp(val userRepository: UserRepository) : UserService {
    override fun checkUser(username: String, password: String): Optional<User> {
        val user = userRepository.findByUsernameAndPassword(username, password)
        return user
    }
}