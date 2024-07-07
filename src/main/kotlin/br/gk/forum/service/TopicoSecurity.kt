package br.gk.forum.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class TopicoSecurity(
    val topicoService: TopicoService
) {
    fun isOwner(id: Long, user: UserDetails): Boolean {
        val topico = topicoService.buscarModelPorId(id)
        return topico.autor.email == user.username
    }
}