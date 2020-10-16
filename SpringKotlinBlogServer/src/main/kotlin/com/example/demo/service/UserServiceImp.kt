package com.example.demo.service

import com.example.demo.dao.UserRepository
import com.example.demo.po.User
import com.example.demo.toMD5
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
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

    override fun listUser(): List<User> {
        return userRepository.findAll()
    }

    @Transactional
    override fun saveUser(user: User) {
        userRepository.save(user)
    }
}