package br.com.promocerva.users.repository

import br.com.promocerva.users.annotations.RealInfra
import br.com.promocerva.users.config.R2DBCConfig
import br.com.promocerva.users.domain.User
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.context.annotation.Import

@DataR2dbcTest
@Import(R2DBCConfig::class)
@RealInfra
class UserRepositoryTest(@Autowired val userRepository: UserRepository) {

    @BeforeEach
    fun setup() {
        runBlocking {
            userRepository.save(User("name", "teste@email.com", "address", 1.0, 2.0, 3.0))
        }
    }

    @Test
    fun `should find user by email`() {
        runBlocking {
            assertThat(userRepository.findByEmail("teste@email.com")).isNotNull
        }
    }

    @AfterEach
    fun teardown() {
        runBlocking {
            userRepository.deleteAll()
        }
    }
}