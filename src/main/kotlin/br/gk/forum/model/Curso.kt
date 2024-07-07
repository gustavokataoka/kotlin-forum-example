package br.gk.forum.model

import jakarta.persistence.Entity

@Entity
data class Curso(
    val nome: String,
    val categoria: String
) : AbstractEntity()