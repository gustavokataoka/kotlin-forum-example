package br.gk.forum.controller

import br.gk.forum.dto.*
import br.gk.forum.service.TopicoService
import jakarta.validation.Valid
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/topicos")
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
        ) pageable: Pageable
    ): PageDto<TopicoView> {
        return topicoService.listar(nomeCurso, pageable)
    }

    @GetMapping("/{id}")
    @Cacheable("topico_view", key = "#id")
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
    @CacheEvict(value = ["topico_view"], key = "#id")
    @PreAuthorize("@topicoSecurity.isOwner(#id, #userDetails)")
    fun atualizar(
        @PathVariable id: Long,
        @RequestBody @Valid editTopicoForm: EditTopicoForm,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<TopicoView> {
        return ResponseEntity.ok(topicoService.atualizar(id, editTopicoForm))
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = ["topico_view"], key = "#id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun excluir(@PathVariable id: Long) {
        topicoService.excluir(id)
    }

    @GetMapping("/relatorio")
    fun relatorio(): List<TopicoPorCategoriaDto> {
        return topicoService.relatorio()
    }

    @GetMapping("/nao-respondidos")
    fun naoRespondidos(): List<TopicoView> {
        return topicoService.naoRespondidos()
    }

}