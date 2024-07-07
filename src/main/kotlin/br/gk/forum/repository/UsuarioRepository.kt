package br.gk.forum.repository

import br.gk.forum.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioRepository : JpaRepository<Usuario, Long> {
    fun findByEmail(username: String): Usuario?
}