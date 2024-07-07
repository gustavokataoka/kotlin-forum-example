package br.gk.forum.exception

class NotFoundException(
    override val message: String = "Recurso não encontrado"
) : RuntimeException(message)