package br.gk.forum.controller

import br.gk.forum.service.RelatorioService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/relatorios/html")
class RelatoriosThymeleafController(
    private val relatorioService: RelatorioService
) {

    @GetMapping("/por-categoria")
    fun porCategoria(model: Model): String {
        model["topicosPorCategorias"] = relatorioService.porCategoria()
        return "relatorios/por-categoria"
    }
}