package br.com.promocerva.users.messaging

import br.com.promocerva.users.domain.User
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.stereotype.Component

@Component
class UserProducer(val streamBridge: StreamBridge) {

    fun newUser(user: User) {
        user.apply { streamBridge.send("newUser", NewUserEvent(name, email, address, locationX to locationY, distance, id)) }
    }
}