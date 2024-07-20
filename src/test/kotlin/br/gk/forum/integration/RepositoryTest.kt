package br.gk.forum.integration

import br.gk.forum.configuration.ContainerConfiguration
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RepositoryTest : ContainerConfiguration() {
    @PersistenceContext
    protected lateinit var em: EntityManager

    @BeforeEach
    fun setUp() {
        println("Iniciando Teste de Integração")
    }
}