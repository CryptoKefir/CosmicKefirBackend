package com.evolvdefi.edefi.service

import com.evolvdefi.edefi.dto.UserDto
import com.evolvdefi.edefi.dto.CreateUserDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.evolvdefi.edefi.repository.UserRepository
import com.evolvdefi.edefi.model.User
import com.evolvdefi.edefi.dto.toDto
import com.evolvdefi.edefi.dto.toEntity

@Service
@Transactional
class UserService(private val userRepository: UserRepository) {
  
  fun createUser(dto: CreateUserDto): UserDto {
    val user = userRepository.save(dto.toEntity())
    return user.toDto()
  }
  fun getUserByEmail(email: String): UserDto? = userRepository.findUserByEmail(email)?.toDto()
  //   fun addWalletToUser(userId: Long, currency: String): Wallet {
  //     val user = userRepository.findById(userId).orElseThrow { NoSuchElementException("User not found") }
  //     return walletRepository.save(Wallet(user = user, currency = currency))
  // }
}
