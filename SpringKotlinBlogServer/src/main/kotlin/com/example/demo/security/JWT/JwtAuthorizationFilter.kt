package com.example.demo.security.JWT

import com.google.gson.GsonBuilder
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SignatureException
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import io.jsonwebtoken.gson.io.GsonDeserializer

class JwtAuthorizationFilter(val authenticationManagerI: AuthenticationManager) : BasicAuthenticationFilter(authenticationManagerI) {
    private val log = LoggerFactory.getLogger(JwtAuthorizationFilter::class.java)

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
//        TODO
        var authentication = getAuthentication(request);
        if (authentication == null) {
            chain.doFilter(request, response);
            return;
        }

        SecurityContextHolder.getContext().authentication = authentication;
        chain.doFilter(request, response)
    }

    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val token = request.getHeader(SecurityConstants.TOKEN_HEADER)
        if (!token.isNullOrEmpty() && token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            try {
                val signingKey = SecurityConstants.JWT_SECRET.toByteArray()
                val map = mapOf("rol" to RolesContainer::class.java)
//                val jd = JacksonDeserializer(map)

                val gson = GsonBuilder().disableHtmlEscaping().create()
                val parsedToken = Jwts.parser()
                        .deserializeJsonWith(GsonDeserializer(gson))
                        .setSigningKey(signingKey)
                        .parseClaimsJws(token.replace("Bearer ", ""))
                val username = parsedToken
                        .body
                        .subject

                val authoritiesRaw = parsedToken.body.get("rol2")
                val json = gson.toJson(authoritiesRaw)
                val authoritiesO = gson.fromJson(json, RolesContainer::class.java)
                val roles = authoritiesO.roles.map {
                    SimpleGrantedAuthority(it)
                }
                if (!username.isNullOrEmpty()) {
                    return UsernamePasswordAuthenticationToken(username, null, roles)
                }
            } catch (exception: ExpiredJwtException) {
//                TODO need to return the error???
                log.warn("Request to parse expired JWT : {} failed : {}", token, exception.message)
            } catch (exception: UnsupportedJwtException) {
                log.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.message)
            } catch (exception: MalformedJwtException) {
                log.warn("Request to parse invalid JWT : {} failed : {}", token, exception.message)
            } catch (exception: SignatureException) {
                log.warn("Request to parse JWT with invalid signature : {} failed : {}", token, exception.message)
            } catch (exception: IllegalArgumentException) {
                log.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.message)
            }
        }

        return null
    }
}