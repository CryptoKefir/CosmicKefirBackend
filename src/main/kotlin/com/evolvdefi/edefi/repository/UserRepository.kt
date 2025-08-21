package com.evolvdefi.edefi.repository

import com.evolvdefi.edefi.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import com.evolvdefi.edefi.dto.UpdateUserDto
import org.springframework.data.jpa.repository.Query

@Repository
interface UserRepository : JpaRepository<User, Long> {
  fun findUserByEmail(email: String): User?
  // @Query("SELECT u FROM User u WHERE u.username = :identifier OR u.email = :identifier")
  fun findByUsernameOrEmail(username: String, email: String): User?
  fun findUserByUsername(username: String): User?
}