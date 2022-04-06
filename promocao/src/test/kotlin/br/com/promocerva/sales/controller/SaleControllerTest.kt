package br.com.promocerva.sales.controller

import br.com.promocerva.sales.domain.Sale
import br.com.promocerva.sales.repository.SaleRepository
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import kotlinx.coroutines.flow.flowOf
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@WebFluxTest
internal class SaleControllerTest(@Autowired val client: WebTestClient) {

    @MockkBean
    private lateinit var repository: SaleRepository

    @Test
    fun `should return a list of all sales`() {
        every { repository.findAll() } returns flowOf(
            Sale("Eisenbahn IPA Lata",
                "Comercial Del Rey",
                "R. São João Del Rei, 350 - Bosque dos Eucaliptos, São José dos Campos - SP, 12233-190",
                GeoJsonPoint(23.2374925,-45.8892112),
                "624ced940a9479336901a254"
            ),
            Sale("IPA Rolleta Russa",
                "Comercial Del Rey",
                "R. São João Del Rei, 350 - Bosque dos Eucaliptos, São José dos Campos - SP, 12233-190",
                GeoJsonPoint(23.2374925,-45.8892112),
                "624ced940a9479336901a254"
            ),
        )
        client.get().uri("/sales").exchange()
            .expectStatus().isOk
            .expectBody()
                .jsonPath("$.length()").isEqualTo(2)
    }

    @Test
    fun `should create a new sale`() {
        coEvery { repository.save(any()) } returnsArgument 0
        val request = """{"description":"description","store":"store","address":"address","location":[0.0,1.0]}"""
        client.post().uri("/sales").contentType(MediaType.APPLICATION_JSON).bodyValue(request)
            .exchange()
            .expectStatus().isCreated
            .expectBody()
                .jsonPath("$.description").isEqualTo("description")
                .jsonPath("$.store").isEqualTo("store")
                .jsonPath("$.address").isEqualTo("address")
                .jsonPath("$.location").isArray
                .jsonPath("$.id").isNotEmpty
        coVerify { repository.save(any()) }
    }

    @Test
    fun `should update an existing sale`() {
        coEvery { repository.findById("abcd") } returns Sale("Eisenbahn IPA Lata",
            "Comercial Del Rey",
            "R. São João Del Rei, 350 - Bosque dos Eucaliptos, São José dos Campos - SP, 12233-190",
            GeoJsonPoint(23.2374925,-45.8892112),
            "abcd"
        )
        coEvery { repository.save(any()) } returnsArgument 0
        val request = """{"description":"description","store":"store","address":"address","location":[0.0,1.0]}"""
        client.put().uri("/sales/abcd").contentType(MediaType.APPLICATION_JSON).bodyValue(request)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.description").isEqualTo("description")
            .jsonPath("$.store").isEqualTo("store")
            .jsonPath("$.address").isEqualTo("address")
            .jsonPath("$.location").isArray
            .jsonPath("$.id").isEqualTo("abcd")
        coVerify { repository.save(any()) }
    }

    @Test
    fun `should delete a sale`() {
        coEvery { repository.deleteById(any()) } returns Unit
        client.delete().uri("/sales/abcd")
            .exchange()
            .expectStatus().isNoContent
        coVerify { repository.deleteById(eq("abcd")) }
    }
}