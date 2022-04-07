package br.com.promocerva.users.controller.dto

import com.fasterxml.jackson.annotation.JsonFormat

data class UserDTO(
    val name: String,
    val email: String,
    val address: String,
    @JsonFormat(shape = JsonFormat.Shape.ARRAY) val location: Pair<Double, Double>,
    val distance: Double,
    val id: Long? = null
)