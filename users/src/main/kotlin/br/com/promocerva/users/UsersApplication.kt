package br.com.promocerva.users

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.core.ReactiveAdapterRegistry
import reactor.blockhound.BlockHound


@SpringBootApplication
class UsersApplication

fun main(args: Array<String>) {
	BlockHound
		.builder()
		.allowBlockingCallsInside("sun.security.provider.NativePRNG\$RandomIO", "readFully")
		.install()
	runApplication<UsersApplication>(*args)
}
