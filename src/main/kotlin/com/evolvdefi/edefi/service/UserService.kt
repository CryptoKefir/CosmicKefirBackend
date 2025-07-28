package com.evolvdefi.edefi.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.evolvdefi.edefi.repository.UserRepository
import com.evolvdefi.edefi.model.User
import com.evolvdefi.edefi.dto.CreateUserDto
import com.evolvdefi.edefi.dto.toEntity

@Service
@Transactional
class UserService(private val userRepository: UserRepository) {
  fun createUser(dto: CreateUserDto): User {
    val user = dto.toEntity()
    return userRepository.save(user)
  }
  fun getAllUsers(): List<User> {
    return userRepository.findAll()
  }
  fun getUserByEmail(email: String): User? {
    return userRepository.findUserByEmail(email)
  }
  fun findById(id: Long): User? =
    userRepository.findById(id).orElse(null)
}