package br.gk.forum.controller

import org.springframework.test.web.servlet.get
import kotlin.test.Test

class TopicoControllerTest : ControllerTest() {// : ContainerConfiguration() {

    companion object {
        private const val RECURSO = "/topicos"
        private const val RECURSO_ID = RECURSO.plus("/%s")
    }

    @Test
    fun `deve retornar codigo 400 quando chamar topicos sem token`() {
        mockMvc.get(RECURSO).andExpect {
            status {
                is4xxClientError()
            }
        }
    }

    @Test
    fun `deve retornar codigo 200 quando chamar topicos com token`() {
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

    @Test
    fun `deve retornar codigo 200 quando chamar topico por id com token`() {
        mockMvc.get(RECURSO_ID.format(1)) {
            headers {
                setBearerAuth(token)
            }
        }.andExpect {
            status {
                isOk()
            }
        }
    }

    @Test
    fun `deve retornar codigo 404 quando chamar topico por id que nao existe com token`() {
        mockMvc.get(RECURSO_ID.format(999999)) {
            headers {
                setBearerAuth(token)
            }
        }.andExpect {
            status {
                isNotFound()
            }
        }
    }
}