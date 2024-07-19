package br.gk.forum.model

import jakarta.persistence.*

@Entity
class Usuario(

    val nome: String,
    val email: String,
    var password: String,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuario_role",
        joinColumns = [JoinColumn(name = "usuario_id", nullable = false)],
        inverseJoinColumns = [JoinColumn(name = "role_id", nullable = false)]
    )
    val roles: List<Role> = mutableListOf()

) : AbstractEntity()