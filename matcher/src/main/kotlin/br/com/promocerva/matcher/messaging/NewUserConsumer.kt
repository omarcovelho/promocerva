package br.com.promocerva.matcher.messaging

import br.com.promocerva.matcher.messaging.events.NewUserEvent
import org.springframework.stereotype.Component
import java.util.function.Consumer

@Component("newUser")
class NewUserConsumer: Consumer<NewUserEvent> {

    override fun accept(event: NewUserEvent) {
        print(event)
    }
}