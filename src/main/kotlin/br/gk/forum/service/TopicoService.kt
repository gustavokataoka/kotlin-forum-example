package br.gk.forum.service

import br.gk.forum.dto.*
import br.gk.forum.exception.NotFoundException
import br.gk.forum.extension.fromEditForm
import br.gk.forum.extension.toPageDTO
import br.gk.forum.mapper.TopicoFormMapper
import br.gk.forum.mapper.TopicoViewMapper
import br.gk.forum.model.Topico
import br.gk.forum.repository.TopicoRepository
import jakarta.transaction.Transactional
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TopicoService(
    private val topicoViewMapper: TopicoViewMapper,
    private val topicoFormMapper: TopicoFormMapper,
    private var topicoRepository: TopicoRepository
) {

    fun listar(nomeCurso: String?, pageable: Pageable): PageDto<TopicoView> {
        val topicos = if (nomeCurso == null) {
            topicoRepository.findAll(pageable)
        } else {
            topicoRepository.findByCursoNome(nomeCurso, pageable)
        }
        return topicos.map { e -> topicoViewMapper.map(e) }.toPageDTO()
    }

    fun buscarModelPorId(id: Long): Topico {
        return topicoRepository.findById(id)
            .orElseThrow { throw NotFoundException(NOT_FOUND) }
    }

    fun buscarPorId(id: Long): TopicoView {
        return buscarModelPorId(id).let {
            topicoViewMapper.map(it)
        }
    }

    @Transactional
    fun cadastrar(novoTopicoForm: NovoTopicoForm): TopicoView {
        val topico = topicoFormMapper.map(novoTopicoForm)
        topicoRepository.save(topico)
        return topicoViewMapper.map(topico)
    }

    @Transactional
    fun atualizar(id: Long, editTopicoForm: EditTopicoForm): TopicoView {
        val topico = buscarModelPorId(id)
        topico.fromEditForm(editTopicoForm)
        topico.dataAlteracao = LocalDateTime.now()
        topicoRepository.save(topico)
        return topico.let { topicoViewMapper.map(it) }
    }

    @Transactional
    fun excluir(id: Long) {
        topicoRepository.deleteById(id)
    }

    fun relatorio(): List<TopicoPorCategoriaDto> {
        return topicoRepository.relatorio()
    }

    fun naoRespondidos(): List<TopicoView> {
        return topicoRepository.topicosNaoRespondidos().map { topicoViewMapper.map(it) }
    }

    companion object {
        const val NOT_FOUND: String = "Tópico não encontrado"
    }

}