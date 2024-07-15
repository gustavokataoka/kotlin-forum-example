package br.gk.forum.model

import org.springframework.security.core.userdetails.UserDetails
import kotlin.reflect.KProperty

class UserPrincipal(
    private val user: Usuario
) : UserDetails {

    override fun getAuthorities() = user.roles

    override fun getPassword() = user.password

    override fun getUsername() = user.email

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true

    companion object {
        fun create(user: Usuario) = UserPrincipal(user)
    }

    fun getUser() = user
}