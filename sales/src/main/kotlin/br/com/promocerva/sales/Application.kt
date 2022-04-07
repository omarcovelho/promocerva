package br.com.promocerva.sales

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SalesApplication

fun main(args: Array<String>) {
	runApplication<SalesApplication>(*args)
}
