package br.gk.forum.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class EditRespostaForm(
    @field:NotEmpty
    @field:Size(min = 5, max = 100)
    val mensagem: String,
)