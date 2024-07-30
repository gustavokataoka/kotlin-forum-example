package br.gk.forum.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

class NovoTopicoForm(

    @field:NotNull(message = "Não pode ser nulo")
    @field:NotEmpty(message = "Título não pode ser em branco")
    @field:Size(min = 5, max = 100, message = "Titulo deve ter entre 5 e 100 caracteres")
    val titulo: String,

    @field:NotNull(message = "Não pode ser nulo")
    @field:NotEmpty(message = "Mensagem não pode ser em branco")
    @field:JsonProperty(required = true)
    val mensagem: String?,

    @field:NotNull
    val idCurso: Long
)