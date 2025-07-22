package com.evolvdefi.edefi.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.evolvdefi.edefi.repository.UserRepository
import com.evolvdefi.edefi.model.User

@Service
@Transactional
class UserService(
  private val userRepository: UserRepository
) {
    // fun getUserByEmail(email: String): User? = userRepo.findByEmail(email)

    fun getUserById(id: Long): User? = userRepository.findByIdOrNull(id)

//   fun createUser(name: String, email: String): User =
//     userRepo.save(User(name = name, email = email))


//   fun addWalletToUser(userId: Long, currency: String): Wallet {
//     val user = userRepo.findById(userId)
//       .orElseThrow { NoSuchElementException("User not found") }
//     return walletRepo.save(Wallet(user = user, currency = currency))
//   }
}
