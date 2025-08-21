package com.evolvdefi.edefi.service

import com.evolvdefi.edefi.dto.CreateUserDto
import com.evolvdefi.edefi.dto.LoginDto
import com.evolvdefi.edefi.dto.UserDto
import com.evolvdefi.edefi.model.User
import com.evolvdefi.edefi.dto.toDto
// import com.evolvdefi.edefi.dto.toEntity
import com.evolvdefi.edefi.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AuthService(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder
) {
  // create user
  fun createUser(dto: CreateUserDto): UserDto {
    val user = User(username = dto.username, email = dto.email, password = passwordEncoder.encode(dto.password))
    if (userRepository.findUserByUsername(user.username) != null) {
      throw IllegalArgumentException("User with username ${user.username} already exists")
    }
    else if (userRepository.findUserByEmail(user.email) != null) {
      throw IllegalArgumentException("User with email ${user.email} already exists")
    }
    return userRepository.save(user).toDto()
  }
  // login
  fun login(dto: LoginDto): UserDto {
    println("dto: ${dto.identifier}")
    val user = userRepository.findByUsernameOrEmail(dto.identifier, dto.identifier)
    println("user: $user")
    if (user == null) {
      throw IllegalArgumentException("no user found with username or email ${dto.identifier}")
    }
    return if (passwordEncoder.matches(dto.password, user.password)) {
      // Success — generate token/session
      // ResponseEntity.ok(mapOf("token" to "<jwt or session‑id>"))
      UserDto(user.id, user.username, user.email)
    } else {
      println("user: ${user.password}")
      println("dto: ${dto.password}")
      println("--------------------------------ooge-------------------------------cachooga")
      throw IllegalArgumentException("incorrect password")
    }
  }
  fun getAllUsers(): List<UserDto> {
    return userRepository.findAll().map { it.toDto() }
  }
  fun deleteUser(userId: Long): UserDto {
    val user = userRepository.findById(userId)
        .orElseThrow { IllegalArgumentException("User with id $userId not found") }
    userRepository.delete(user)
    return user.toDto()
  }
}
