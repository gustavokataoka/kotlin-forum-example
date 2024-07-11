package br.gk.forum.repository.specification

import br.gk.forum.model.Curso
import br.gk.forum.model.Topico
import jakarta.persistence.criteria.*
import org.springframework.data.jpa.domain.Specification
import org.springframework.web.bind.annotation.RequestParam

class TopicoSpecification(

    @RequestParam(required = true)
    val titulo: String?,

    @RequestParam(required = false)
    val mensagem: String?,

    @RequestParam(required = false)
    val curso: String?

) : Specification<Topico> {

    override fun toPredicate(
        root: Root<Topico>,
        query: CriteriaQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate? {

        val predicates: MutableList<Predicate> = mutableListOf()

        titulo?.let {
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