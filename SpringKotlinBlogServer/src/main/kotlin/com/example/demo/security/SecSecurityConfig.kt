package com.example.demo.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter


@Configuration
@EnableWebSecurity
class SecSecurityConfig(private val passwordConfig: PasswordConfig, private val applicationUserService: ApplicationUserService) : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        // authentication manager (see below)
        /*  auth.inMemoryAuthentication()
                  .withUser("user1").password(passwordConfig.passwordEncoder().encode("user1Pass")).roles("USER")
                  .and()
                  .withUser("user2").password(passwordConfig.passwordEncoder().encode("user2Pass")).roles("USER")*/

        auth.authenticationProvider(daoAuthenticationProvide())


    }


    override fun configure(http: HttpSecurity) {
        // http builder configurations for authorize requests and form login (see below)
        http.csrf().disable().authorizeRequests()
                .antMatchers("/admin/**").hasRole("USER")
                ?.antMatchers("/**")?.permitAll()
                ?.anyRequest()?.authenticated()?.and()
                ?.formLogin()
                ?.loginPage("/admin")?.permitAll()
                ?.loginProcessingUrl("/perform_login")
                ?.defaultSuccessUrl("/admin/blogs")

    }

    @Bean
    fun daoAuthenticationProvide(): DaoAuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setPasswordEncoder(passwordConfig.passwordEncoder())
        provider.setUserDetailsService(applicationUserService)

        return provider

    }

}