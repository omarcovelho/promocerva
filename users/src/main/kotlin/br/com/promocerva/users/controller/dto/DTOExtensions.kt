package br.com.promocerva.users.controller.dto

import br.com.promocerva.users.domain.User

fun UserDTO.toEntity() = User(name, email, address, location.first, location.second, distance, id)

fun User.toDto() = UserDTO(name, email, address, locationX to locationY, distance, id)