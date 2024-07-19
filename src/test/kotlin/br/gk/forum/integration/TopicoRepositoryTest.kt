package br.gk.forum.integration

import br.gk.forum.dto.TopicoPorCategoriaDto
import br.gk.forum.model.TopicoTest
import br.gk.forum.repository.TopicoRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import kotlin.test.Test

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TopicoRepositoryTest {

    val topico = TopicoTest.build()

    @Autowired
    private lateinit var topicoRepository: TopicoRepository

    @PersistenceContext
    private lateinit var em: EntityManager

    companion object {

        @Container
        private val pgsqlContainer = PostgreSQLContainer<Nothing>("postgres:latest").apply {
            withDatabaseName("testdb")
            withUsername("pgtest")
            withPassword("123456")
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", pgsqlContainer::getJdbcUrl)
            registry.add("spring.datasource.password", pgsqlContainer::getPassword)
            registry.add("spring.datasource.username", pgsqlContainer::getUsername)
            registry.add("spring.datasource.driver-class-name", pgsqlContainer::getDriverClassName)
        }
    }

    @BeforeEach
    fun setUp() {
        em.persist(topico.autor)
        em.persist(topico.curso)
        em.persist(topico)
    }

    @Test
    fun `deve gerar um relatorio`() {

        val relatorio = topicoRepository.relatorio()

        assertThat(relatorio).isNotNull
        assertThat(relatorio.first()).isExactlyInstanceOf(TopicoPorCategoriaDto::class.java)
    }

    @Test
    fun `deve listar topico pelo nome do curso`() {

        val topico = topicoRepository.findByCursoNome(topico.curso.nome, PageRequest.of(0, 5))

        assertThat(topico).isNotNull
    }
}