package br.gk.forum.model

object TopicoTest {
    fun build() = Topico(
        titulo = "Kotlin Basico",
        mensagem = "Aprendendo kotlin basico",
        curso = CursoTest.build(),
        autor = UsuarioTest.build()
    )
}