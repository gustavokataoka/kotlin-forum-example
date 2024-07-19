package br.gk.forum.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Topico(
    var titulo: String,
    var mensagem: String,
    val dataCriacao: LocalDateTime = LocalDateTime.now(),
    var dataAlteracao: LocalDateTime? = null,

    @ManyToOne(cascade = [CascadeType.MERGE])
    val curso: Curso,

    @ManyToOne(cascade = [CascadeType.MERGE])
    val autor: Usuario,

    @Enumerated(value = EnumType.STRING)
    val status: StatusTopico = StatusTopico.NAO_RESPONDIDO,

    @OneToMany(mappedBy = "topico", cascade = [CascadeType.MERGE])
    val respostas: List<Resposta> = ArrayList(),

) : AbstractEntity()