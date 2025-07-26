package com.evolvdefi.edefi.repository

import com.evolvdefi.edefi.model.Wallet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WalletRepository : JpaRepository<Wallet, Long> {
  fun findByUserId(userId: Long): List<Wallet>
  fun findByUserIdAndCurrency(userId: Long, currency: String): Wallet?
}