package br.com.promocerva.users.controller

import br.com.promocerva.users.config.R2DBCConfig
import br.com.promocerva.users.domain.User
import br.com.promocerva.users.repository.UserRepository
import br.com.promocerva.users.repository.UserRepositoryTest
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import kotlinx.coroutines.flow.flowOf
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@WebFluxTest(excludeAutoConfiguration = [R2DBCConfig::class])
internal class UsersControllerTest(@Autowired val client: WebTestClient) {

    @MockkBean
    private lateinit var repository: UserRepository

    @Test
    fun `should return a list of users`() {
        every { repository.findAll() } returns flowOf(
            User("user1", "user1@email.com", "address1", 1.0, 1.1, 1.0, 1),
            User("user2", "user2@email.com", "address2", 2.0, 2.2, 2.0, 2)
        )

        client.get().uri("/api/users").exchange()
            .expectStatus().isOk
            .expectBody()
                .jsonPath("$.length()").isEqualTo(2)
    }

    @Test
    fun `should create a user`() {
        coEvery { repository.save(any()) } answers {
            with(firstArg<User>()) { User(name, email, address, locationX, locationY, distance, 1) }
        }

        coEvery { repository.findByEmail(any()) } returns null
        val request = """{
            "name": "Marco Prado",
            "email": "marco.prado@gmail.com",
            "address": "Rua Lafayete, 255",
            "location": [-23.2374925,-45.8892112],
            "distance": 10.5
        }"""
        client.post().uri("/api/users").contentType(MediaType.APPLICATION_JSON).bodyValue(request)
            .exchange()
            .expectStatus().isCreated
            .expectBody()
                .jsonPath("$.name").isEqualTo("Marco Prado")
                .jsonPath("$.email").isEqualTo("marco.prado@gmail.com")
                .jsonPath("$.address").isEqualTo("Rua Lafayete, 255")
                .jsonPath("$.location").isArray
                .jsonPath("$.distance").isEqualTo(10.5)
                .jsonPath("$.id").isEqualTo(1)

        coVerify { repository.save(any()) }
        coVerify { repository.findByEmail(eq("marco.prado@gmail.com")) }
    }

    @Test
    fun `should return bad request for existing email`() {
        coEvery { repository.findByEmail(any()) } returns User("name", "email", "address", 0.0, 0.0, 0.0, 1)

        val request = """{
            "name": "Marco Prado",
            "email": "marco.prado@gmail.com",
            "address": "Rua Lafayete, 255",
            "location": [-23.2374925,-45.8892112],
            "distance": 10.5
        }"""
        return client.post().uri("/api/users").contentType(MediaType.APPLICATION_JSON).bodyValue(request)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody().returnResult().toString().let { print(it) }
    }

    @Test
    fun `should update an existing user`() {
        coEvery { repository.findById(1) } returns
                User("user1", "user1@email.com", "address1", 1.0, 1.1, 1.0, 1)

        coEvery { repository.save(any()) } returnsArgument 0

        val request = """{
            "name": "Marco Prado",
            "email": "marco.prado@gmail.com",
            "address": "Rua Lafayete, 255",
            "location": [-23.2374925,-45.8892112],
            "distance": 10.5
        }"""

        client.put().uri("/api/users/1").contentType(MediaType.APPLICATION_JSON).bodyValue(request)
            .exchange()
            .expectStatus().isOk
            .expectBody()
                .jsonPath("$.name").isEqualTo("Marco Prado")
                .jsonPath("$.email").isEqualTo("user1@email.com")
                .jsonPath("$.address").isEqualTo("Rua Lafayete, 255")
                .jsonPath("$.location").isArray
                .jsonPath("$.distance").isEqualTo(10.5)
                .jsonPath("$.id").isEqualTo(1)

        coVerify { repository.findById(1) }
        coVerify { repository.save(any()) }
    }

    @Test
    fun `should delete a user`() {
        coEvery { repository.deleteById(1) } returns Unit

        client.delete().uri("/api/users/1")
            .exchange()
            .expectStatus().isNoContent

        coVerify { repository.deleteById(1) }
    }
}