package br.gk.forum.service

import br.gk.forum.dto.TopicoPorCategoriaDto
import br.gk.forum.dto.TopicoView
import br.gk.forum.mapper.TopicoViewMapper
import br.gk.forum.repository.TopicoRepository
import org.springframework.stereotype.Service

@Service
class RelatorioService(
    private val topicoViewMapper: TopicoViewMapper,
    private val topicoRepository: TopicoRepository
) {

    fun porCategoria(): List<TopicoPorCategoriaDto> {
        return topicoRepository.relatorio()
    }

    fun naoRespondidos(): List<TopicoView> {
        return topicoRepository.topicosNaoRespondidos().map { topicoViewMapper.map(it) }
    }

}
