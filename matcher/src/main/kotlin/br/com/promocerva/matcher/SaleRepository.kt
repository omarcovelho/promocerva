package br.com.promocerva.matcher

import br.com.promocerva.matcher.domain.Sale
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface SaleRepository: ReactiveMongoRepository<Sale, String> {
}