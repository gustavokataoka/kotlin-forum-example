package br.gk.forum.extension

import br.gk.forum.dto.EditTopicoForm
import br.gk.forum.model.Topico

fun Topico.fromEditForm(editTopicoForm: EditTopicoForm) {
    this.titulo = editTopicoForm.titulo
    this.mensagem = editTopicoForm.mensagem
}