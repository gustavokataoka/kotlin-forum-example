package br.gk.forum.service

import br.gk.forum.dto.EditRespostaForm
import br.gk.forum.dto.NovaRespostaForm
import br.gk.forum.dto.RespostaView
import br.gk.forum.exception.NotFoundException
import br.gk.forum.extension.fromEditForm
import br.gk.forum.mapper.RespostaViewMapper
import br.gk.forum.model.Resposta
import br.gk.forum.repository.RespostaRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class RespostaService(
    private val respostaRepository: RespostaRepository,
    private val respostaViewMapper: RespostaViewMapper,
    private val topicoService: TopicoService,
    private val emailService: EmailService,
    private val customUserDetailsService: CustomUserDetailsService
) {
    fun listar(idTopico: Long): List<RespostaView> {
        return respostaRepository.findAll()
            .map { e -> respostaViewMapper.map(e) }
    }

    fun buscarModelPorId(id: Long): Resposta {
        return respostaRepository.findById(id)
            .orElseThrow{ NotFoundException("Resposta não encontrada") }
    }

    fun buscarPorId(id: Long): RespostaView {
        return buscarModelPorId(id).let { respostaViewMapper.map(it) }
    }

    @Transactional
    fun cadastrar(id: Long, respostaForm: NovaRespostaForm): RespostaView {
        val topico = topicoService.buscarModelPorId(id)
        val user = customUserDetailsService.getAuthenticatedUser()
        val resposta = Resposta(
            mensagem = respostaForm.mensagem,
            autor = user,
            topico = topico
        )
        respostaRepository.save(resposta)
        emailService.notificarRecebimentoResposta(topico, user)
        return respostaViewMapper.map(resposta)
    }

    @Transactional
    fun atualizar(id: Long, editRespostaForm: EditRespostaForm): RespostaView {
        val resposta = buscarModelPorId(id)
        resposta.fromEditForm(editRespostaForm)
        respostaRepository.save(resposta)
        return resposta.let { respostaViewMapper.map(it) }
    }

    @Transactional
    fun excluir(id: Long) {
        respostaRepository.deleteById(id)
    }

}
