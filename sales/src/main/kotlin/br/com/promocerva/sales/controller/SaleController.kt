package br.com.promocerva.sales.controller

import br.com.promocerva.sales.controller.dto.SaleDTO
import br.com.promocerva.sales.controller.dto.toDto
import br.com.promocerva.sales.controller.dto.toEntity
import br.com.promocerva.sales.messaging.SaleProducer
import br.com.promocerva.sales.repository.SaleRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/sales")
class SaleController(
    private val saleRepository: SaleRepository,
    private val saleProducer: SaleProducer) {

    @GetMapping
    suspend fun list(): Flow<SaleDTO> {
        return saleRepository.findAll()
            .map { it.toDto() }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun create(@RequestBody sale: SaleDTO): SaleDTO {
        return saleRepository.save(sale.toEntity()).let {
            saleProducer.newSale(it)
            it.toDto()
        }
    }

    @PutMapping("/{id}")
    suspend fun update(@PathVariable("id") id: String, @RequestBody sale: SaleDTO): SaleDTO {
        return saleRepository.findById(id)
            ?.let { it.update(sale) }
            ?.let { saleRepository.save(it).toDto() }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun delete(@PathVariable("id") id: String) {
        saleRepository.deleteById(id)
    }
}