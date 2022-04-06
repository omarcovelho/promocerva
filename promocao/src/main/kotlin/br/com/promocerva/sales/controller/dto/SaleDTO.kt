package br.com.promocerva.sales.controller.dto

import com.fasterxml.jackson.annotation.JsonFormat

data class SaleDTO(
    val description: String,
    val store: String,
    val address: String,
    @JsonFormat(shape = JsonFormat.Shape.ARRAY) val location: Pair<Double, Double>,
    val id: String? = null
)