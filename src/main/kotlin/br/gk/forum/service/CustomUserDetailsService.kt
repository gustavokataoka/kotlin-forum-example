package br.gk.forum.service

import br.gk.forum.exception.NotFoundException
import br.gk.forum.model.UserPrincipal
import br.gk.forum.repository.UsuarioRepository
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
}