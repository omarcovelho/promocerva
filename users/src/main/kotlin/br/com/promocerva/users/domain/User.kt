package br.com.promocerva.users.domain

import br.com.promocerva.users.controller.dto.UserDTO
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("USERS")
class User(
    @Column("NAME") var name: String,
    @Column("EMAIL") val email: String,
    @Column("ADDRESS") var address: String,
    @Column("LOCATION_X") var locationX: Double,
    @Column("LOCATION_Y") var locationY: Double,
    @Column("DISTANCE") var distance: Double,
    @Id @Column("ID") val id: Long? = null
) {
    fun update(user: UserDTO): User {
        return apply {
            name = user.name
            address = user.address
            locationX = user.location.first
            locationY = user.location.second
            distance = user.distance
        }

    }
}
