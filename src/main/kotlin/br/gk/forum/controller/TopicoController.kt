package br.gk.forum.controller

import br.gk.forum.dto.*
import br.gk.forum.repository.specification.TopicoSpecification
import br.gk.forum.service.TopicoService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearerAuth")
@Validated
class TopicoController(
    private val topicoService: TopicoService
) {

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    fun listar(
        @RequestParam(required = false) nomeCurso: String?,
        @PageableDefault(
            size = 5,
            sort = ["dataCriacao"],
            direction = Sort.Direction.DESC
        ) @ParameterObject pageable: Pageable
    ): PageDto<TopicoView> {
        return topicoService.listar(nomeCurso, pageable)
    }

    @GetMapping("/buscar")
    fun buscar(
        @Valid topicoSpecification: TopicoSpecification,
        @PageableDefault(
            size = 5,
            sort = ["dataCriacao"],
            direction = Sort.Direction.DESC
        ) @ParameterObject pageable: Pageable
    ): PageDto<TopicoView> {
        return topicoService.buscar(topicoSpecification, pageable)
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): ResponseEntity<TopicoView> {
        return ResponseEntity.ok(topicoService.buscarPorId(id))
    }

    @PostMapping
    fun cadastrar(
        @RequestBody @Valid novoTopicoForm: NovoTopicoForm,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<TopicoView> {
        val topicoView = topicoService.cadastrar(novoTopicoForm)
        val uri = uriBuilder.path("/topicos/${topicoView.id}").build().toUri()
        return ResponseEntity.created(uri).body(topicoView)
    }

    @PutMapping("/{id}")
    @PreAuthorize("@topicoSecurity.isOwner(#id, #userDetails)")
    fun atualizar(
        @PathVariable id: Long,
        @RequestBody @Valid editTopicoForm: EditTopicoForm,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<TopicoView> {
        return ResponseEntity.ok(topicoService.atualizar(id, editTopicoForm))
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@topicoSecurity.isOwner(#id, #userDetails)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun excluir(
        @PathVariable id: Long,
        @AuthenticationPrincipal userDetails: UserDetails
    ) {
        topicoService.excluir(id)
    }

}