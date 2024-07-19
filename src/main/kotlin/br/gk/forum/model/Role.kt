package br.gk.forum.model

import jakarta.persistence.Entity
import org.springframework.security.core.GrantedAuthority

@Entity
class Role(
    val nome: String
) : AbstractEntity(), GrantedAuthority {
    override fun getAuthority() = nome
}
