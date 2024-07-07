package br.gk.forum.controller

import br.gk.forum.dto.AuthForm
import br.gk.forum.dto.AuthView
import br.gk.forum.service.AuthenticationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    val authenticationService: AuthenticationService
) {

    @PostMapping
    fun authenticate(@RequestBody authForm: AuthForm): AuthView =
        authenticationService.authenticate(authForm)
}