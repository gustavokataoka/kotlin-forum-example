package br.gk.forum.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Topico(
    var titulo: String,
    var mensagem: String,
    val dataCriacao: LocalDateTime = LocalDateTime.now(),
    var dataAlteracao: LocalDateTime? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", referencedColumnName = "id")
    val curso: Curso,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", referencedColumnName = "id")
    val autor: Usuario,

    @Enumerated(value = EnumType.STRING)
    val status: StatusTopico = StatusTopico.NAO_RESPONDIDO,

    @OneToMany(mappedBy = "topico", fetch = FetchType.LAZY)
    val respostas: List<Resposta> = ArrayList(),

) : AbstractEntity()