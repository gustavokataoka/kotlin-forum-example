package br.gk.forum.configuration

import org.junit.jupiter.api.BeforeAll
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer

abstract class ContainerConfiguration {

    companion object {

        @JvmStatic
        private val pgsqlContainer = PostgreSQLContainer<Nothing>("postgres:latest").apply {
            withDatabaseName("testdb")
            withUsername("pgtest")
            withPassword("123456")
            withReuse(true)
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", this.pgsqlContainer::getJdbcUrl)
            registry.add("spring.datasource.password", this.pgsqlContainer::getPassword)
            registry.add("spring.datasource.username", this.pgsqlContainer::getUsername)
            registry.add("spring.datasource.driver-class-name", this.pgsqlContainer::getDriverClassName)
        }

        @JvmStatic
        @BeforeAll
        fun startAllContainers(): Unit {
            pgsqlContainer.start()
        }
    }
}