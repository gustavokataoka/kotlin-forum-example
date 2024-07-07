package br.gk.forum.mapper

import br.gk.forum.dto.RespostaView
import br.gk.forum.model.Resposta
import org.springframework.stereotype.Component

@Component
class RespostaViewMapper : Mapper<Resposta, RespostaView> {
    override fun map(t: Resposta): RespostaView {
        return RespostaView(
            id = t.id,
            mensagem = t.mensagem,
            dataCriacao = t.dataCriacao,
            autor = t.autor.nome
        )
    }
}