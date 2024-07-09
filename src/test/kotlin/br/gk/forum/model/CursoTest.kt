package br.gk.forum.model

object CursoTest {
    fun build() = Curso(
        nome = "Java",
        categoria = "Programação"
    ).also { it.id = 1 }
}