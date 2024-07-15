package br.gk.forum.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class RespostaSecurity(
    val respostaService: RespostaService
) {
    fun isOwner(id: Long, user: UserDetails): Boolean {
        val responsta = respostaService.buscarModelPorId(id)
        return responsta.autor.email == user.username
    }
}