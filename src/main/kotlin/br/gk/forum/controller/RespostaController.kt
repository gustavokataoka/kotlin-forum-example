package br.gk.forum.controller

import br.gk.forum.dto.EditRespostaForm
import br.gk.forum.dto.NovaRespostaForm
import br.gk.forum.dto.RespostaView
import br.gk.forum.service.RespostaService
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/topicos/{idTopico}/respostas")
class RespostaController(private val respostaService: RespostaService) {

    @GetMapping
    fun listar(@PathVariable idTopico: Long): List<RespostaView> {
        return respostaService.listar(idTopico)
    }

    @PostMapping
    fun cadastrar(
        @PathVariable idTopico: Long,
        @RequestBody @Valid respostaForm: NovaRespostaForm,
        uriBuilder: UriComponentsBuilder,
        request: HttpServletRequest
    ): ResponseEntity<RespostaView> {
        val respostaView = respostaService.cadastrar(idTopico, respostaForm)
        val uri = uriBuilder.path(request.servletPath).build().toUri()
        return ResponseEntity.created(uri).body(respostaView)
    }

    @PutMapping("/{id}")
    fun atualizar(
        @PathVariable id: Long,
        @PathVariable idTopico: String,
        @RequestBody @Valid editRespostaForm: EditRespostaForm
    ): ResponseEntity<RespostaView> {
        return ResponseEntity.ok(respostaService.atualizar(id, editRespostaForm))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun excluir(@PathVariable id: Long, @PathVariable idTopico: String) {
        respostaService.excluir(id)
    }

}