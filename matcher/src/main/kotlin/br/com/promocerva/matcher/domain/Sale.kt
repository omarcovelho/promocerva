package br.com.promocerva.matcher.domain

import com.fasterxml.jackson.annotation.JsonFormat
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
)