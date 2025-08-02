package com.evolvdefi.edefi.repository

import com.evolvdefi.edefi.model.BitcoinWallet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WalletRepository : JpaRepository<BitcoinWallet, Long> {
  fun findByUserId(userId: Long): List<BitcoinWallet>
  fun findByUserIdAndNetwork(userId: Long, network: String): BitcoinWallet?
}