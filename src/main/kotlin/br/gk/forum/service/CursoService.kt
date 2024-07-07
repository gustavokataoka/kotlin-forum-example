package br.gk.forum.service

import br.gk.forum.exception.NotFoundException
import br.gk.forum.model.Curso
import br.gk.forum.repository.CursoRepository
import org.springframework.stereotype.Service

@Service
class CursoService(
    private val cursoRepository: CursoRepository
) {
    fun buscarPorId(id: Long): Curso {
        return cursoRepository.findById(id)
            .orElseThrow { NotFoundException("Curso não encontrado") }
    }
}