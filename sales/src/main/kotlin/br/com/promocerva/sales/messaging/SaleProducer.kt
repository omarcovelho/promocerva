package br.com.promocerva.sales.messaging

import br.com.promocerva.sales.domain.Sale
import br.com.promocerva.sales.messaging.events.NewSaleEvent
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.stereotype.Component

@Component
class SaleProducer(private val streamBridge: StreamBridge) {

    suspend fun newSale(sale: Sale) {
        sale.apply {
            streamBridge.send(
                "newSale",
                NewSaleEvent(description, store, address, location.x to location.y, id!!))
        }
    }
}