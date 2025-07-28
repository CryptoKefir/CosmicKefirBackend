package com.evolvdefi.edefi.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.evolvdefi.edefi.repository.WalletRepository
import com.evolvdefi.edefi.model.Wallet
import com.evolvdefi.edefi.dto.CreateWalletDto
import com.evolvdefi.edefi.dto.UpdateWalletBalanceDto
import com.evolvdefi.edefi.dto.WalletDto
import com.evolvdefi.edefi.dto.toEntity
import com.evolvdefi.edefi.dto.toDto
import java.math.BigDecimal


@Service
@Transactional
class WalletService(
  private val walletRepository: WalletRepository
) {
  // Create a wallet for a user
  fun createWalletForUser(createWalletDto: CreateWalletDto): WalletDto {
    if(walletRepository.findByUserIdAndCurrency(createWalletDto.userId, createWalletDto.currency) != null){
      throw IllegalArgumentException("User $createWalletDto.userId already has a wallet for $createWalletDto.currency")
    }
    val wallet = createWalletDto.toEntity();
    return walletRepository.save(wallet).toDto();
  }
  // Get a list of a user's wallet
  fun getWalletsForUser(userId: Long): List<WalletDto>{
    return walletRepository.findByUserId(userId).map(Wallet::toDto)
  }
  // Update a user's wallet balance for a specific currency
  fun updateWalletBalance(userId: Long, currency: String, updateWalletBalanceDto: UpdateWalletBalanceDto): WalletDto {
    var wallet = walletRepository.findByUserIdAndCurrency(userId, currency)
    if(wallet == null){
      throw IllegalArgumentException("User $userId doesn't have a wallet for $currency")
    }
    wallet.balance = updateWalletBalanceDto.balance
    return walletRepository.save(wallet).toDto()
  }
  // Delete a wallet by wallet ID
  fun deleteWallet(id: Long){
    val wallet = walletRepository.findById(id)
      .orElseThrow { IllegalArgumentException("Wallet with id $id not found") }
    walletRepository.delete(wallet)
  }
  
}