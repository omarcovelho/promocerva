package br.com.promocerva.sales

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.core.ReactiveAdapterRegistry
import reactor.blockhound.BlockHound
import reactor.core.scheduler.ReactorBlockHoundIntegration

@SpringBootApplication
class SalesApplication

fun main(args: Array<String>) {

//	BlockHound.install(
//		ReactorBlockHoundIntegration(),
//		ReactiveAdapterRegistry.SpringCoreBlockHoundIntegration()
//	)
	runApplication<SalesApplication>(*args)
}
