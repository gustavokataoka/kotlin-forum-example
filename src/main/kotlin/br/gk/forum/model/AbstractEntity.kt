package br.gk.forum.model

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass

@MappedSuperclass
abstract class AbstractEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null
)