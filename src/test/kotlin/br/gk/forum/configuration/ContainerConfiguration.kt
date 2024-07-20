package br.gk.forum.configuration

import org.junit.jupiter.api.BeforeAll
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
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

        private val redisContainer = GenericContainer<Nothing>("redis:latest").apply {
            withExposedPorts(6379)
            withReuse(true)
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", this.pgsqlContainer::getJdbcUrl)
            registry.add("spring.datasource.password", this.pgsqlContainer::getPassword)
            registry.add("spring.datasource.username", this.pgsqlContainer::getUsername)
            registry.add("spring.datasource.driver-class-name", this.pgsqlContainer::getDriverClassName)

            registry.add("spring.data.redis.host", redisContainer::getHost)
            registry.add("spring.data.redis.port", redisContainer::getFirstMappedPort)
        }

        @JvmStatic
        @BeforeAll
        fun startAllContainers() {
            pgsqlContainer.start()
            redisContainer.start()
        }
    }
}