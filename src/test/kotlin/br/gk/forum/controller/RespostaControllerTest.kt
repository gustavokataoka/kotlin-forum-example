package br.gk.forum.controller

import org.springframework.test.web.servlet.get
import kotlin.test.Test

class RespostaControllerTest : ControllerTest() {

    companion object {
        private const val RECURSO = "/topicos/1/respostas"
    }

    @Test
    fun `deve retornar codigo 400 quando chamar resposta sem token`() {
        mockMvc.get(RECURSO).andExpect {
            status {
                is4xxClientError()
            }
        }
    }

    @Test
    fun `deve retornar codigo 200 quando chamar respostas com token`() {
        mockMvc.get(RECURSO) {
            headers {
                setBearerAuth(token)
            }
        }.andExpect {
            status {
                isOk()
            }
        }
    }
}