package com.example.demo.security

import com.example.demo.service.UserService
import com.example.demo.unwrap
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class ApplicationUserService(private val userService: UserService) : UserDetailsService {
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(p0: String): UserDetails {
        val user = userService.getUser(p0).unwrap()

        if (user != null)
            return user
        else
            throw UsernameNotFoundException("user not fround")
    }
}