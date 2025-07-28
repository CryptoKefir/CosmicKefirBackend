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
  // create user
  fun createUser(dto: CreateUserDto): UserDto {
    val user = dto.toEntity()
    if (userRepository.findUserByEmail(user.email) != null) {
      throw IllegalArgumentException("User with email ${user.email} already exists")
    }
    return userRepository.save(user).toDto()
  }
  // get all users
  fun getAllUsers(): List<UserDto> {
    return userRepository.findAll()?.map { it.toDto() } ?: emptyList()
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
        name = dto.name,
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