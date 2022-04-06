package br.com.promocerva.sales.controller.dto

import br.com.promocerva.sales.domain.Sale
import org.springframework.data.mongodb.core.geo.GeoJsonPoint

fun SaleDTO.toEntity() = Sale(
    description, store, address, GeoJsonPoint(location.first, location.second))

fun Sale.toDto() = SaleDTO(
    description, store, address, location.x to location.y, id)