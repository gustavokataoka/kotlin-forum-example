package br.gk.forum.controller

import br.gk.forum.service.TokenService
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.util.*
import kotlin.test.Test

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TopicoControllerTest {

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    @Autowired
    private lateinit var tokenService: TokenService

    private lateinit var mockMvc: MockMvc

    private lateinit var token: String

    companion object {
        private const val RECURSO = "/topicos"
    }

    @BeforeEach
    fun setUp() {

        token = generateTokenForTest()

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply<DefaultMockMvcBuilder>(
                SecurityMockMvcConfigurers.springSecurity()
            )
            .build()
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

    private fun generateTokenForTest(): String {
        val user = userDetailsService.loadUserByUsername("gustavo.kataoka@gmail.com")
        return tokenService.generate(user, Date(System.currentTimeMillis() + 3600000))
    }
}