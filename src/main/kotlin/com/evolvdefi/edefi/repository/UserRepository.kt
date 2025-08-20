package com.evolvdefi.edefi.repository

import com.evolvdefi.edefi.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import com.evolvdefi.edefi.dto.UpdateUserDto

@Repository
interface UserRepository : JpaRepository<User, Long> {
  fun findUserByEmail(email: String): User?
  fun findByUsernameOrEmail(username: String, email: String): User?
}