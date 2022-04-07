package br.com.promocerva.sales.domain

import br.com.promocerva.sales.controller.dto.SaleDTO
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.mapping.Document

@Document("sales")
data class Sale(
    var description: String,
    var store: String,
    var address: String,
    var location: GeoJsonPoint,
    @Id val id: String? = null
) {
    fun update(sale: SaleDTO): Sale {
        return apply {
            description = sale.description
            store = sale.store
            address = sale.address
            location = GeoJsonPoint(sale.location.first, sale.location.second)
        }
    }
}
