package com.evolvdefi.edefi.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.evolvdefi.edefi.repository.UserRepository
import com.evolvdefi.edefi.model.User
import com.evolvdefi.edefi.dto.CreateUserDto
import com.evolvdefi.edefi.dto.UserDto
import com.evolvdefi.edefi.dto.toEntity
import com.evolvdefi.edefi.dto.toDto
import com.evolvdefi.edefi.dto.UpdateUserDto

@Service
@Transactional
class UserService(private val userRepository: UserRepository) {
  // get all users
  fun getAllUsers(): List<UserDto> {
    return userRepository.findAll().map { it.toDto() }
  }
  // get user by email
  fun getUserByEmail(email: String): UserDto? {
    return userRepository.findUserByEmail(email)?.toDto()
  }
  // update user
  fun updateUser(dto: UpdateUserDto): UserDto {
    val user = userRepository.findById(dto.id)
        .orElseThrow { IllegalArgumentException("User with id ${dto.id} not found") }
    val updatedUser = user.copy(
        username = dto.username,
        email = dto.email,
        password = dto.password
    )
    return userRepository.save(updatedUser).toDto()
  }
  // delete user
  fun deleteUser(userId: Long): UserDto {
    val user = userRepository.findById(userId)
        .orElseThrow { IllegalArgumentException("User with id $userId not found") }
    userRepository.delete(user)
    return user.toDto()
  }
  fun findById(id: Long): User? =
    userRepository.findById(id).orElse(null)
}