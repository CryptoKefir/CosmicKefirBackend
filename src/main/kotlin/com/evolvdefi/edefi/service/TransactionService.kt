package com.evolvdefi.edefi.service

import org.springframework.stereotype.Service
import org.bitcoindevkit.*
import java.math.BigDecimal
import com.evolvdefi.edefi.repository.WalletRepository
import com.evolvdefi.edefi.model.BitcoinWallet
import com.evolvdefi.edefi.model.TransactionInfo

@Service
class TransactionService(
    private val walletRepository: WalletRepository
) {
    //check if the wallet exists
    fun checkWalletExists(userId: Long, network: String): Boolean {
        return walletRepository.findByUserIdAndNetwork(userId, network) != null
    }
    
    fun getBalance(userId: Long, network: String): BigDecimal {
        val bitcoinWallet = walletRepository.findByUserIdAndNetwork(userId, network)
            ?: throw IllegalArgumentException("Wallet not found for user $userId on network $network")
        
        // Create wallet from stored descriptors
        val wallet = createWalletFromDescriptors(bitcoinWallet.externalDescriptor, bitcoinWallet.internalDescriptor)
        val balance = wallet.getBalance()
        return BigDecimal.valueOf(balance.total)
    }
    
    fun sendTransaction(userId: Long, network: String, toAddress: String, amount: BigDecimal): String {
        val bitcoinWallet = walletRepository.findByUserIdAndNetwork(userId, network)
            ?: throw IllegalArgumentException("Wallet not found for user $userId on network $network")
        
        // Create wallet from stored descriptors
        val wallet = createWalletFromDescriptors(bitcoinWallet.externalDescriptor, bitcoinWallet.internalDescriptor)
        
        // For now, return a mock transaction ID
        // Full transaction implementation requires blockchain connection
        return "mock_tx_id_${System.currentTimeMillis()}"
    }
    
    fun getTransactionHistory(userId: Long, network: String): List<TransactionInfo> {
        val bitcoinWallet = walletRepository.findByUserIdAndNetwork(userId, network)
            ?: throw IllegalArgumentException("Wallet not found for user $userId on network $network")
        
        // For now, return empty list
        // Full implementation requires blockchain connection
        return emptyList()
    }
    
    private fun createWalletFromDescriptors(externalDescriptor: String, internalDescriptor: String): Wallet {
        val externalDesc = Descriptor(externalDescriptor, Network.TESTNET)
        val internalDesc = Descriptor(internalDescriptor, Network.TESTNET)
        
        return Wallet(
            descriptor = externalDesc,
            changeDescriptor = internalDesc,
            network = Network.TESTNET,
            databaseConfig = DatabaseConfig.Memory
        )
    }
} 