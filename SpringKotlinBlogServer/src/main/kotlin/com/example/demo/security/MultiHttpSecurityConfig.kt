package com.example.demo.security

import com.example.demo.errorHandle.createRestError
import com.example.demo.security.JWT.JwtAuthenticationFilter
import com.example.demo.security.JWT.JwtAuthorizationFilter
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetailsService
import java.io.PrintWriter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@EnableWebSecurity
class MultiHttpSecurityConfig {
    @Autowired
    private lateinit var passwordConfig: PasswordConfig

    @Autowired
    private lateinit var applicationUserService: UserDetailsService

    @Bean
    fun daoAuthenticationProvide(): DaoAuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setPasswordEncoder(passwordConfig.passwordEncoder())
        provider.setUserDetailsService(applicationUserService)

        return provider

    }

    @Configuration
    @Order(1)
    class ApiWebSecurityConfigurationAdapter : BasicSecurityConfigurerAdapter() {

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


//            TODO

//            http.antMatcher("/api/**")
//                    .exceptionHandling().authenticationEntryPoint { httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse, authenticationException: AuthenticationException ->
//                        /*      val gson = Gson()
//                              val json = gson.toJson(createRestError(authenticationException.message ?: ""))
//                              val out: PrintWriter = httpServletResponse.getWriter()
//                              httpServletResponse.contentType = "application/json"
//                              httpServletResponse.characterEncoding = "UTF-8"
//                              out.print(json)
//                              out.flush()*/
//
//                        createRestError(authenticationException.message
//                                ?: "").addErrorToResponseAJson(httpServletResponse)
//
//                    }
        }

    }


    @Configuration
    @Order(2)
    class FormLoginWebSecurityConfigurerAdapter : BasicSecurityConfigurerAdapter() {


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


    abstract class BasicSecurityConfigurerAdapter : WebSecurityConfigurerAdapter() {
        @Autowired
        private lateinit var passwordConfig: PasswordConfig

        @Autowired
        private lateinit var daoAuthenticationProvide: DaoAuthenticationProvider

        override fun configure(web: WebSecurity) {
            web.ignoring().antMatchers("/css/**", "/images/**", "/lib/**")
        }

        @Throws(Exception::class)
        override fun configure(auth: AuthenticationManagerBuilder) {
            // authentication manager (see below)
            /* auth.inMemoryAuthentication()
                     .withUser("user1").password(passwordConfig.passwordEncoder().encode("user1Pass")).roles("USER")
                     .and()
                     .withUser("user2").password(passwordConfig.passwordEncoder().encode("user2Pass")).roles("USER")*/

            auth.authenticationProvider(daoAuthenticationProvide)


        }
    }
}