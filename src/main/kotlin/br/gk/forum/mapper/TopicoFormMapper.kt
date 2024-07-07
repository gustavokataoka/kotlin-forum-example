package br.gk.forum.mapper

import br.gk.forum.dto.NovoTopicoForm
import br.gk.forum.model.Topico
import br.gk.forum.service.CursoService
import br.gk.forum.service.UsuarioService
import org.springframework.stereotype.Component

@Component
class TopicoFormMapper(
    private val serviceCurso: CursoService,
    private val serviceUsuario: UsuarioService,
) : Mapper<NovoTopicoForm, Topico> {
    override fun map(t: NovoTopicoForm): Topico {
        return Topico(
            titulo = t.titulo,
            mensagem = t.mensagem,
            curso = serviceCurso.buscarPorId(t.idCurso),
            autor = serviceUsuario.buscarPorId(t.idAutor)
        )
    }
}