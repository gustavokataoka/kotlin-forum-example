package br.gk.forum.mapper

import br.gk.forum.dto.NovoTopicoForm
import br.gk.forum.model.Topico
import org.springframework.stereotype.Component

@Component
class TopicoFormMapper : Mapper<NovoTopicoForm, Topico?> {
    override fun map(t: NovoTopicoForm): Topico? {
        return null
    }
}