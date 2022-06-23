package br.com.promocerva.matcher.messaging.events

import com.fasterxml.jackson.annotation.JsonFormat

data class NewSaleEvent(
    val description: String,
    val store: String,
    val address: String,
    @JsonFormat(shape = JsonFormat.Shape.ARRAY) val location: Pair<Double, Double>,
    val id: String
)
