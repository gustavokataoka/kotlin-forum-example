package br.gk.forum.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class NovaRespostaForm(
    @field:NotEmpty(message = "Mensagem não pode ser em branco")
    @field:Size(min = 5, max = 100, message = "Mensagem deve ter entre 5 e 100 caracteres")
    val mensagem: String
)