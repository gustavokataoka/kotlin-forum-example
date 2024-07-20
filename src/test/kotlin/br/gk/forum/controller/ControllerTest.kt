package br.gk.forum.controller

import br.gk.forum.configuration.ContainerConfiguration
import br.gk.forum.service.TokenService
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class ControllerTest : ContainerConfiguration() {

    @Autowired
    protected lateinit var webApplicationContext: WebApplicationContext

    @Autowired
    protected lateinit var userDetailsService: UserDetailsService

    @Autowired
    protected lateinit var tokenService: TokenService

    protected lateinit var mockMvc: MockMvc

    protected lateinit var token: String

    @BeforeEach
    fun setUp() {

        token = generateTokenForTest()

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply<DefaultMockMvcBuilder>(
                SecurityMockMvcConfigurers.springSecurity()
            )
            .build()
    }

    protected fun generateTokenForTest(): String {
        val user = userDetailsService.loadUserByUsername("gustavo.kataoka@gmail.com")
        return tokenService.generate(user, Date(System.currentTimeMillis() + 3600000))
    }
}