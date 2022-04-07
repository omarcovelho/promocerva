package br.com.promocerva.users.repository

import br.com.promocerva.users.domain.User
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface UserRepository : CoroutineCrudRepository<User, Long>{
    suspend fun findByEmail(email: String): User?
}
