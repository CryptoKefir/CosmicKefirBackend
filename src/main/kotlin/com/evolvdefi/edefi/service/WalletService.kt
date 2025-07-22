package com.evolvdefi.edefi.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.evolvdefi.edefi.repository.WalletRepository
import com.evolvdefi.edefi.model.Wallet

@Service
@Transactional
class WalletService(
  private val walletRepo: WalletRepository
) {
//   fun getUserByEmail(email: String): User? = userRepo.findByEmail(email)

//   fun createUser(name: String, email: String): User =
//     userRepo.save(User(name = name, email = email))

  fun getWalletsForUser(userId: Long): List<Wallet> =
    walletRepo.findByUserId(userId)
      .also { /* Optionally, ensure user exists */ }

//   fun addWalletToUser(userId: Long, currency: String): Wallet {
//     val user = userRepo.findById(userId)
//       .orElseThrow { NoSuchElementException("User not found") }
//     return walletRepo.save(Wallet(user = user, currency = currency))
//   }
}