package br.gk.forum.repository

import br.gk.forum.dto.TopicoPorCategoriaDto
import br.gk.forum.model.Topico
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TopicoRepository : JpaRepository<Topico, Long> {
    fun findByCursoNome(nomeCurso: String, pageable: Pageable): Page<Topico>

    @Query("SELECT new br.gk.forum.dto.TopicoPorCategoriaDto(c.categoria, COUNT(t)) " +
            "FROM Topico t JOIN t.curso c GROUP BY c.categoria")
    fun relatorio(): List<TopicoPorCategoriaDto>

    @Query("SELECT t FROM Topico t WHERE t.respostas IS EMPTY")
    fun topicosNaoRespondidos(): List<Topico>
}