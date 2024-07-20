package br.gk.forum.integration

import br.gk.forum.dto.TopicoPorCategoriaDto
import br.gk.forum.model.TopicoTest
import br.gk.forum.repository.TopicoRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import kotlin.test.Test

class TopicoRepositoryTest : RepositoryTest() {

    val topico = TopicoTest.build()

    @Autowired
    private lateinit var topicoRepository: TopicoRepository

    @BeforeEach
    override fun setUp() {
        super.setUp()
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