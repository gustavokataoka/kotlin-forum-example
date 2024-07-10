package br.gk.forum.model

object UsuarioTest {
    fun build() = Usuario(
        nome = "John Doe",
        email = "john@test.com",
        password = "123",
        roles = listOf(Role("ADMIN"))
    )
}