package com.evolvdefi.edefi.repository

import com.evolvdefi.edefi.model.CKWallet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WalletRepository : JpaRepository<CKWallet, Long> {
  fun findByUserId(userId: Long): List<CKWallet>
  fun findByUserIdAndNetwork(userId: Long, network: String): CKWallet?
}