package br.gk.forum.service

import br.gk.forum.model.Topico
import br.gk.forum.model.Usuario
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService(
    private val javaMailSender: JavaMailSender
) {

    fun notificarRecebimentoResposta(topico: Topico, usuario: Usuario) {
        if (topico.autor.id == usuario.id) {
            return
        }

        val message = SimpleMailMessage()

        message.subject = "Resposta"
        message.text = "Olá. Você recebeu uma resposta no forum."
        message.setTo(topico.autor.email)

        javaMailSender.send(message)
    }
}