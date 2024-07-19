package br.gk.forum.model

import jakarta.persistence.Entity

@Entity
class Curso(
    val nome: String,
    val categoria: String
) : AbstractEntity()