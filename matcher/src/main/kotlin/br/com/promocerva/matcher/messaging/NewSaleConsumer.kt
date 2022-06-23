package br.com.promocerva.matcher.messaging

import br.com.promocerva.matcher.SaleRepository
import br.com.promocerva.matcher.domain.Sale
import br.com.promocerva.matcher.messaging.events.NewSaleEvent
import kotlinx.coroutines.coroutineScope
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.util.function.Consumer

@Component("newSale")
class NewSaleConsumer(val saleRepository: SaleRepository): Consumer<Flux<NewSaleEvent>> {

    override fun accept(event: Flux<NewSaleEvent>) {
        event
            .log()
            .flatMap { saleRepository.save(Sale(it.description, it.store, it.address, GeoJsonPoint(it.location.first, it.location.second), it.id)) }
            .log()
            .subscribe()
//        event.apply {
//            saleRepository.save()
//        }
    }
}