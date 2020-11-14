package com.example.demo.security.JWT

import com.example.demo.po.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

data class RolesContainer(val roles: List<String>) {
}

class JwtAuthenticationFilter(private val authenticationManagerI: AuthenticationManager) : UsernamePasswordAuthenticationFilter() {


    init {
        setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL)
    }


    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val requestURI = request.requestURI
        val username = request.getParameter("username")
        val password = request.getParameter("password")
        val authenticationToken = UsernamePasswordAuthenticationToken(username, password)

        return authenticationManagerI.authenticate(authenticationToken);
    }


    override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain, authentication: Authentication) {

        val user = authentication.principal as User
        val roles = user.authorities

        val rolesString = roles.map {
            it.authority
        }

        val signingKey = SecurityConstants.JWT_SECRET.toByteArray()
        val token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject(user.username)
                .setExpiration(Date(System.currentTimeMillis() + 100))
                .claim("rol2", RolesContainer(rolesString))
                .compact()
        response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + token);
    }
}