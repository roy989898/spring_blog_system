package com.example.demo.security

import com.example.demo.security.JWT.JwtAuthenticationFilter
import com.example.demo.security.JWT.JwtAuthorizationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy

@EnableWebSecurity
class MultiHttpSecurityConfig(private val passwordConfig: PasswordConfig, private val applicationUserService: ApplicationUserService) {

    @Bean
    fun daoAuthenticationProvide(): DaoAuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setPasswordEncoder(passwordConfig.passwordEncoder())
        provider.setUserDetailsService(applicationUserService)

        return provider

    }

    @Configuration
    @Order(1)
    class ApiWebSecurityConfigurationAdapter : WebSecurityConfigurerAdapter() {

        override fun configure(http: HttpSecurity) {
            http
                    .antMatcher("/api/**")
                    .csrf().disable()
                    .cors().disable()
                    .authorizeRequests()
                    .antMatchers("/api/private").hasRole("USER")
                    .antMatchers("/**").permitAll()
                    .and()
                    .antMatcher("/api/**")
                    .addFilter(JwtAuthenticationFilter(authenticationManager()))
                    .addFilter(JwtAuthorizationFilter(authenticationManager()))
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }

    }


    @Configuration
    @Order(2)
    class FormLoginWebSecurityConfigurerAdapter : WebSecurityConfigurerAdapter() {

        override fun configure(http: HttpSecurity) {
            http
                    .antMatcher("/**")
                    .authorizeRequests()
                    .antMatchers("/admin/**").hasRole("USER")
                    .antMatchers("/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/admin").permitAll()
                    .loginProcessingUrl("/perform_login")
                    .defaultSuccessUrl("/admin/blogs")
        }

    }

    @Configuration
    @Order(3)
    class BasicSecurityConfigurerAdapter : WebSecurityConfigurerAdapter() {
        override fun configure(web: WebSecurity) {
            web.ignoring().antMatchers("/css/**", "/images/**", "/lib/**")
        }
    }
}