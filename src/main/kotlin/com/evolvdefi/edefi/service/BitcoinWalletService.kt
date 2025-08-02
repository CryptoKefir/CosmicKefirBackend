package com.evolvdefi.edefi.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.evolvdefi.edefi.repository.WalletRepository
import com.evolvdefi.edefi.service.UserService
import com.evolvdefi.edefi.model.User
import com.evolvdefi.edefi.model.BitcoinWallet
import com.evolvdefi.edefi.dto.CreateWalletDto
import com.evolvdefi.edefi.dto.UpdateWalletBalanceDto
import com.evolvdefi.edefi.dto.WalletDto
import com.evolvdefi.edefi.dto.toEntity
import com.evolvdefi.edefi.dto.toDto
import java.math.BigDecimal

import org.bitcoindevkit.*

@Service
@Transactional
class WalletService(
  private val walletRepository: WalletRepository,
  private val userService: UserService
) {
  // Create a wallet for a user
  fun createWalletForUser(createWalletDto: CreateWalletDto): WalletDto {
    val userId = createWalletDto.userId
    val network = createWalletDto.network
    val user = userService.findById(userId)
    val mnemonic = Mnemonic(WordCount.WORDS12)
    println("Here's the seed: $mnemonic")
    // Create a DescriptorSecretKey bound to Testnet and using an empty passphrase
    val descriptorSecretKey = DescriptorSecretKey(
        network = Network.TESTNET,
        mnemonic = mnemonic,
        password = ""
    )
    println("Generated Master (Z‑seed) + account tprv:\n${descriptorSecretKey.asString()}\n" +
            "⚠️ Caution: the output includes the private master key and account node. Don't log this on MainNet!")
    
     // Build BIP‑86 descriptors (external & internal) via the template helper
    val externalDescriptor = Descriptor.newBip86(
        secretKey = descriptorSecretKey,
        keychain = KeychainKind.EXTERNAL,
        network = Network.TESTNET
    )
    val internalDescriptor = Descriptor.newBip86(
        secretKey = descriptorSecretKey,
        keychain = KeychainKind.INTERNAL,
        network = Network.TESTNET
    )

    println("--------- Descriptors ---------")
    println("External:  ${externalDescriptor.toString()}")
    println("Internal:  ${internalDescriptor.toString()}")
    if(user == null){
      throw IllegalArgumentException("No user with id = $userId")
    }
    if(walletRepository.findByUserIdAndNetwork(userId, network) != null){
      throw IllegalArgumentException("User with ID = $userId already has a wallet for $network")
    }

    val bitcoinWallet = BitcoinWallet(user = user, network = Network.TESTNET.toString(), externalDescriptor = externalDescriptor.toString(), internalDescriptor = internalDescriptor.toString(), status = "active")
    return walletRepository.save(bitcoinWallet).toDto()
  }
  // Get a list of a user's wallet
  fun getWalletsForUser(userId: Long): List<WalletDto>{
    val user = userService.findById(userId)
    if(user == null){
      throw IllegalArgumentException("No user with ID = $userId")
    }
    return walletRepository.findByUserId(userId).map(BitcoinWallet::toDto)
  }
  // Update a user's wallet balance for a specific network
  fun updateWalletBalance(userId: Long, network: String, updateWalletBalanceDto: UpdateWalletBalanceDto): WalletDto {
    var bitcoinWallet = walletRepository.findByUserIdAndNetwork(userId, network)
    if(bitcoinWallet == null){
      throw IllegalArgumentException("User with ID = $userId doesn't have a wallet for $network")
    }
    bitcoinWallet.balance = updateWalletBalanceDto.balance
    return walletRepository.save(bitcoinWallet).toDto()
  }
  // Delete a wallet by wallet ID
  fun deleteWallet(id: Long){
    val bitcoinWallet = walletRepository.findById(id)
      .orElseThrow { IllegalArgumentException("Wallet with ID = $id not found") }
    walletRepository.delete(bitcoinWallet)
  }
  
}