package br.com.promocerva.sales.repository

import br.com.promocerva.sales.domain.Sale
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface SaleRepository : CoroutineCrudRepository<Sale, String> {

}
