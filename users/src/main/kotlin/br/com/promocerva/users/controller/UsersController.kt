package br.com.promocerva.users.controller

import br.com.promocerva.users.controller.dto.UserDTO
import br.com.promocerva.users.controller.dto.toDto
import br.com.promocerva.users.controller.dto.toEntity
import br.com.promocerva.users.messaging.NewUserEvent
import br.com.promocerva.users.messaging.UserProducer
import br.com.promocerva.users.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.nio.file.Files
import java.nio.file.Path

@RestController
@RequestMapping("/api/users")
class UsersController(
    val userRepository: UserRepository,
    val userProducer: UserProducer) {

    @GetMapping
    fun list(): Flow<UserDTO> {
        return userRepository.findAll()
            .map { it.toDto() }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun create(@RequestBody user: UserDTO): UserDTO {
        userRepository.findByEmail(user.email)?.let { throw ResponseStatusException(HttpStatus.BAD_REQUEST, "email already exists") }

        return user.toEntity().let {
            userRepository.save(it)
            userProducer.newUser(it)
            it.toDto()
        }
    }

    @PutMapping("/{id}")
    suspend fun update(@PathVariable id: Long, @RequestBody user: UserDTO): UserDTO {
        return userRepository.findById(id)
            ?.let { it.update(user) }
            ?.let { userRepository.save(it).toDto() }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun delete(@PathVariable id: Long) {
        userRepository.deleteById(id)
    }
}