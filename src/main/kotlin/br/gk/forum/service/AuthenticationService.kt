package br.gk.forum.service

import br.gk.forum.cofiguration.JwtProperties
import br.gk.forum.dto.AuthForm
import br.gk.forum.dto.AuthView
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationService(
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties,
    private val authenticationProvider: AuthenticationProvider
) {

    fun authenticate(authForm: AuthForm): AuthView {
        authenticationProvider.authenticate(
            UsernamePasswordAuthenticationToken(
                authForm.username,
                authForm.password
            )
        )

        val user = userDetailsService.loadUserByUsername(authForm.username)

        val accessToken = tokenService.generate(
            userDetails = user,
            expirationDate = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)
        )

        return AuthView(accessToken = accessToken)
    }
}