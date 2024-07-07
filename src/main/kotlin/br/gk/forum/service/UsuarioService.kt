package br.gk.forum.service

import br.gk.forum.exception.NotFoundException
import br.gk.forum.model.Usuario
import br.gk.forum.repository.UsuarioRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UsuarioService(
    private val usuarioRepository: UsuarioRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun buscarPorId(id: Long): Usuario {
        return usuarioRepository.findById(id)
            .orElseThrow { NotFoundException("Usuário não encontrado") }
    }

    fun cadastrar (usuario: Usuario) {
        val updated = usuario.copy(
            password = passwordEncoder.encode(usuario.password)
        )
        usuarioRepository.save(updated)
    }
}