package com.evolvdefi.edefi.service

import com.evolvdefi.edefi.dto.CreateUserDto
import com.evolvdefi.edefi.dto.LoginDto
import com.evolvdefi.edefi.dto.UserDto
import com.evolvdefi.edefi.dto.toDto
import com.evolvdefi.edefi.dto.toEntity
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
    val user = dto.toEntity()
    if (userRepository.findUserByEmail(user.email) != null) {
      throw IllegalArgumentException("User with email ${user.email} already exists")
    }
    return userRepository.save(user).toDto()
  }
  // login
  fun login(dto: LoginDto): UserDto {
    val user = userRepository.findByUsernameOrEmail(dto.identifier, dto.identifier)
    if (user == null) {
      throw IllegalArgumentException("ooga")
    }

    return if (passwordEncoder.matches(dto.password, user.password)) {
      // Success — generate token/session
      // ResponseEntity.ok(mapOf("token" to "<jwt or session‑id>"))
      UserDto(user.id, user.username, user.email)
    } else {
      throw IllegalArgumentException("booga")
    }
  }
}
