package br.gk.forum.service

import br.gk.forum.dto.EditTopicoForm
import br.gk.forum.dto.NovoTopicoForm
import br.gk.forum.dto.PageDto
import br.gk.forum.dto.TopicoView
import br.gk.forum.exception.NotFoundException
import br.gk.forum.extension.fromEditForm
import br.gk.forum.extension.toPageDTO
import br.gk.forum.mapper.TopicoViewMapper
import br.gk.forum.model.Topico
import br.gk.forum.repository.TopicoRepository
import br.gk.forum.repository.specification.TopicoSpecification
import jakarta.transaction.Transactional
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TopicoService(
    private val topicoViewMapper: TopicoViewMapper,
    private var topicoRepository: TopicoRepository,
    private val customUserDetailsService: CustomUserDetailsService,
    private val cursoService: CursoService
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

    @Cacheable("topico_view", key = "#id")
    fun buscarPorId(id: Long): TopicoView {
        return buscarModelPorId(id).let {
            topicoViewMapper.map(it)
        }
    }

    @Transactional
    fun cadastrar(novoTopicoForm: NovoTopicoForm): TopicoView {
        val topico = Topico(
            titulo = novoTopicoForm.titulo,
            mensagem = novoTopicoForm.mensagem ?: "",
            curso = cursoService.buscarPorId(novoTopicoForm.idCurso),
            autor = customUserDetailsService.getAuthenticatedUser()
        )
        topicoRepository.save(topico)
        return topicoViewMapper.map(topico)
    }

    @Transactional
    @CacheEvict(value = ["topico_view"], key = "#id")
    fun atualizar(id: Long, editTopicoForm: EditTopicoForm): TopicoView {
        val topico = buscarModelPorId(id)
        topico.fromEditForm(editTopicoForm)
        topico.dataAlteracao = LocalDateTime.now()
        topicoRepository.save(topico)
        return topico.let { topicoViewMapper.map(it) }
    }

    @Transactional
    @CacheEvict(value = ["topico_view"], key = "#id")
    fun excluir(id: Long) {
        topicoRepository.deleteById(id)
    }

    fun buscar(topicoSpecification: TopicoSpecification, pageable: Pageable): PageDto<TopicoView> {
        return topicoRepository.findAll(topicoSpecification, pageable)
            .map { e -> topicoViewMapper.map(e) }.toPageDTO()
    }

    companion object {
        const val NOT_FOUND: String = "Tópico não encontrado"
    }

}