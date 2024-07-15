package br.gk.forum.service

import br.gk.forum.exception.NotFoundException
import br.gk.forum.mapper.TopicoViewMapper
import br.gk.forum.model.TopicoTest
import br.gk.forum.model.TopicoViewTest
import br.gk.forum.repository.TopicoRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.util.*

class TopicoServiceTest {

    private val topicos = PageImpl(listOf(TopicoTest.build()))

    private val topicoViewMapperMock = mockk<TopicoViewMapper> {
        every { map(any()) } returns TopicoViewTest.build()
    }

    private val paginacao: Pageable = mockk()

    private val customUserDetailsServiceMock: CustomUserDetailsService = mockk()

    private val cursoServiceMock: CursoService = mockk()

    private val topicoRepositoryMock = mockk<TopicoRepository> {
        every {
            findByCursoNome(any(), any())
        } returns topicos

        every {
            findAll(paginacao)
        } returns topicos
    }

    private val topicoService = TopicoService(
        topicoViewMapper = topicoViewMapperMock,
        topicoRepository = topicoRepositoryMock,
        customUserDetailsService = customUserDetailsServiceMock,
        cursoService = cursoServiceMock
    )

    @Test
    fun `deve listar topicos a partir do nome do curso`() {

        topicoService.listar("Kotlin avançado", paginacao)

        verify(exactly = 1) { topicoRepositoryMock.findByCursoNome(any(), any()) }
        verify(exactly = 1) { topicoViewMapperMock.map(any()) }
        verify(exactly = 0) { topicoRepositoryMock.findAll(paginacao) }
    }

    @Test
    fun `deve listar todos os topicos quando nome do curso for nulo`() {

        topicoService.listar(null, paginacao)

        verify(exactly = 0) { topicoRepositoryMock.findByCursoNome(any(), any()) }
        verify(exactly = 1) { topicoViewMapperMock.map(any()) }
        verify(exactly = 1) { topicoRepositoryMock.findAll(paginacao) }
    }

    @Test
    fun `deve listar not found exception quando o topico nao for achado`() {

        every {
            topicoRepositoryMock.findById(any())
        } returns Optional.empty()

        val atual = assertThrows<NotFoundException> {
            topicoService.buscarPorId(1)
        }

        assertThat(atual.message).isEqualTo(TopicoService.NOT_FOUND)
    }

    @Test
    fun `deve retornar o topico for achado`() {

        val topicoMock = TopicoTest.build()

        every {
            topicoRepositoryMock.findById(any())
        } returns Optional.of(topicoMock)

        val topico = topicoService.buscarPorId(1)

        assertThat(topico.titulo).isEqualTo(topicoMock.titulo)
    }

}