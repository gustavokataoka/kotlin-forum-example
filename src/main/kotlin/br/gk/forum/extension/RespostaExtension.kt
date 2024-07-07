package br.gk.forum.extension

import br.gk.forum.dto.EditRespostaForm
import br.gk.forum.model.Resposta

fun Resposta.fromEditForm(editRespostaForm: EditRespostaForm) {
    this.mensagem = editRespostaForm.mensagem
}