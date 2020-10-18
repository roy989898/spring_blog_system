package com.example.demo.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter


@Configuration
@EnableWebSecurity
class SecSecurityConfig : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        // authentication manager (see below)
        auth.inMemoryAuthentication()
                .withUser("user1").password(passwordEncoder().encode("user1Pass")).roles("USER")
                .and()
                .withUser("user2").password(passwordEncoder().encode("user2Pass")).roles("USER")


    }

    override fun configure(http: HttpSecurity) {
        // http builder configurations for authorize requests and form login (see below)
        http.csrf().disable().authorizeRequests()
                .antMatchers("/admin/**").hasRole("USER")
                ?.antMatchers("/**")?.permitAll()
                ?.anyRequest()?.authenticated()?.and()?.httpBasic()

    }
}