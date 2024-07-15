package br.gk.forum.repository.specification

import br.gk.forum.model.Curso
import br.gk.forum.model.Topico
import io.swagger.v3.oas.annotations.Parameter
import jakarta.persistence.criteria.*
import jakarta.validation.constraints.NotNull
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.jpa.domain.Specification

@ParameterObject
class TopicoSpecification (

    @field:NotNull(message = "O título deve ser informado")
    @field:Parameter(required = true, name = "titulo", description = "Busca pelo título do tópico")
    val titulo: String,

    val mensagem: String?,

    val curso: String?

) : Specification<Topico> {

    override fun toPredicate(
        root: Root<Topico>,
        query: CriteriaQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate? {

        val predicates: MutableList<Predicate> = mutableListOf()

        titulo.let {
            predicates.add(criteriaBuilder.like(root.get("titulo"), "%$it%"))
        }

        mensagem?.let {
            predicates.add(criteriaBuilder.like(root.get("mensagem"), "%$it%"))
        }

        curso?.let {
            val joinCurso = root.join<Topico, Curso>("curso", JoinType.LEFT)
            predicates.add(criteriaBuilder.like(joinCurso.get("nome"), "%$it%"))
        }

        return criteriaBuilder.and(*predicates.toTypedArray())

    }
}