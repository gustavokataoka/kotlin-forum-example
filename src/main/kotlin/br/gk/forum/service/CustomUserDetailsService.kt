package br.gk.forum.service

import br.gk.forum.exception.NotFoundException
import br.gk.forum.model.UserPrincipal
import br.gk.forum.model.Usuario
import br.gk.forum.repository.UsuarioRepository
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val usuarioRepository: UsuarioRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val usuario = usuarioRepository.findByEmail(username)
            ?: throw NotFoundException("Usuário não encontrado")
        return UserPrincipal.create(usuario)
    }

    fun getAuthenticatedUser(): Usuario {
        val auth = SecurityContextHolder.getContext().authentication as Authentication
        return (auth.principal as UserPrincipal).getUser()
    }
}