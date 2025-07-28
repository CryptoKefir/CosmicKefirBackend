package com.evolvdefi.edefi.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.evolvdefi.edefi.repository.WalletRepository
import com.evolvdefi.edefi.service.UserService
import com.evolvdefi.edefi.model.Wallet
import com.evolvdefi.edefi.model.User
import com.evolvdefi.edefi.dto.CreateWalletDto
import com.evolvdefi.edefi.dto.UpdateWalletBalanceDto
import com.evolvdefi.edefi.dto.WalletDto
import com.evolvdefi.edefi.dto.toEntity
import com.evolvdefi.edefi.dto.toDto
import java.math.BigDecimal


@Service
@Transactional
class WalletService(
  private val walletRepository: WalletRepository,
  private val userService: UserService
) {
  // Create a wallet for a user
  fun createWalletForUser(createWalletDto: CreateWalletDto): WalletDto {
    val userId = createWalletDto.userId
    val currency = createWalletDto.currency
    val user = userService.findById(userId)
    if(user == null){
      throw IllegalArgumentException("No user with id = $userId")
    }
    if(walletRepository.findByUserIdAndCurrency(userId, currency) != null){
      throw IllegalArgumentException("User with ID = $userId already has a wallet for $currency")
    }
    val wallet = Wallet(user = user, currency = currency)
    return walletRepository.save(wallet).toDto()
  }
  // Get a list of a user's wallet
  fun getWalletsForUser(userId: Long): List<WalletDto>{
    val user = userService.findById(userId)
    if(user == null){
      throw IllegalArgumentException("No user with ID = $userId")
    }
    return walletRepository.findByUserId(userId).map(Wallet::toDto)
  }
  // Update a user's wallet balance for a specific currency
  fun updateWalletBalance(userId: Long, currency: String, updateWalletBalanceDto: UpdateWalletBalanceDto): WalletDto {
    var wallet = walletRepository.findByUserIdAndCurrency(userId, currency)
    if(wallet == null){
      throw IllegalArgumentException("User with ID = $userId doesn't have a wallet for $currency")
    }
    wallet.balance = updateWalletBalanceDto.balance
    return walletRepository.save(wallet).toDto()
  }
  // Delete a wallet by wallet ID
  fun deleteWallet(id: Long){
    val wallet = walletRepository.findById(id)
      .orElseThrow { IllegalArgumentException("Wallet with ID = $id not found") }
    walletRepository.delete(wallet)
  }
  
}