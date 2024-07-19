package br.gk.forum.controller

import br.gk.forum.dto.TopicoPorCategoriaDto
import br.gk.forum.dto.TopicoView
import br.gk.forum.service.RelatorioService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/relatorios")
class RelatorioController(
    private val relatorioService: RelatorioService
) {

    @GetMapping("/por-categoria")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun porCategoria(): List<TopicoPorCategoriaDto> {
        return relatorioService.porCategoria()
    }

    @GetMapping("/nao-respondidos")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun naoRespondidos(): List<TopicoView> {
        return relatorioService.naoRespondidos()
    }
}