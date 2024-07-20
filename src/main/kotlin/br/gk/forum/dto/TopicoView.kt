package br.gk.forum.dto

import br.gk.forum.model.StatusTopico
import java.io.Serializable
import java.time.LocalDateTime

data class TopicoView(
    val id: Long?,
    val titulo: String,
    val mensagem: String,
    val status: StatusTopico,
    val dataCriacao: LocalDateTime,
    val dataAlteracao: LocalDateTime?
) : Serializable
