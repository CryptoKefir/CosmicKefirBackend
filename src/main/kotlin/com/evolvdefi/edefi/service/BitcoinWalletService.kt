package com.evolvdefi.edefi.service

import com.evolvdefi.edefi.dto.CreateWalletDto
import com.evolvdefi.edefi.dto.UpdateWalletBalanceDto
import com.evolvdefi.edefi.dto.WalletDto
import com.evolvdefi.edefi.dto.SendTransactionDto
import com.evolvdefi.edefi.dto.toDto
import com.evolvdefi.edefi.model.BitcoinWallet
import com.evolvdefi.edefi.repository.BitcoinWalletRepository
import java.io.File
import java.math.BigDecimal
import org.bitcoindevkit.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BitcoinWalletService(
        private val walletRepository: BitcoinWalletRepository,
        private val userService: UserService
) {
        val bitcoinNetwork = Network.SIGNET
        val ESPLORA_URL = "https://blockstream.info/signet/api"
        // Create a wallet for a user
        fun createWalletForUser(createWalletDto: CreateWalletDto): WalletDto {
                val userId = createWalletDto.userId
                val user = userService.findById(userId)
                if (user == null) {
                        throw IllegalArgumentException("No user with id = $userId")
                }

                val PERSISTENCE_FILE_PATH: String = "user${userId}Wallet.sqlite"
                println(PERSISTENCE_FILE_PATH)
                val persistenceExists = File(PERSISTENCE_FILE_PATH).exists()
                if (persistenceExists) {
                        throw IllegalArgumentException(
                                "User with id = $userId already has a bitcoin wallet"
                        )
                }
                val mnemonic = Mnemonic(WordCount.WORDS12)
                println("Here's the seed: $mnemonic")
                // Create a DescriptorSecretKey bound to Testnet and using an empty passphrase
                val descriptorSecretKey =
                        DescriptorSecretKey(
                                network = bitcoinNetwork,
                                mnemonic = mnemonic,
                                password = ""
                        )
                println(
                        "Generated Master (Z‑seed) + account tprv:\n${descriptorSecretKey.asString()}\n" +
                                "⚠️ Caution: the output includes the private master key and account node. Don't log this on MainNet!"
                )

                // Build BIP‑86 descriptors (external & internal) via the template helper
                val externalDescriptor =
                        Descriptor.newBip86(
                                secretKey = descriptorSecretKey,
                                keychain = KeychainKind.EXTERNAL,
                                network = bitcoinNetwork
                        )
                val internalDescriptor =
                        Descriptor.newBip86(
                                secretKey = descriptorSecretKey,
                                keychain = KeychainKind.INTERNAL,
                                network = bitcoinNetwork
                        )

                println("--------- Descriptors ---------")
                println("External:  ${externalDescriptor.toStringWithSecret()}")
                println("Internal:  ${internalDescriptor.toStringWithSecret()}")

                val bitcoinWallet =
                        BitcoinWallet(
                                user = user,
                                network = bitcoinNetwork.toString(),
                                externalDescriptor = externalDescriptor.toStringWithSecret(),
                                internalDescriptor = internalDescriptor.toStringWithSecret(),
                                status = "active"
                        )

                val connection = Connection(PERSISTENCE_FILE_PATH)
                println("wawaweewa")
                println("exists: " + persistenceExists)
                val bdkWallet =
                        Wallet(
                                descriptor = externalDescriptor,
                                changeDescriptor = internalDescriptor,
                                network = bitcoinNetwork,
                                connection = connection
                        )
                println("bamba")

                val esploraClient: EsploraClient = EsploraClient(ESPLORA_URL)
                println("ooga")
                val fullScanRequest: FullScanRequest = bdkWallet.startFullScan().build()
                println("booga: " + fullScanRequest.toString())
                val update =
                        esploraClient.fullScan(
                                request = fullScanRequest,
                                stopGap = 10uL,
                                parallelRequests = 1uL
                        )
                println("cachooga")
                bdkWallet.applyUpdate(update)
                println("sambooka")
                val balance = bdkWallet.balance().total.toSat()
                println("Balance: $balance")
                println("darbooka")
                if (balance.toLong().toBigDecimal() != bitcoinWallet.balance) {
                        bitcoinWallet.balance = balance.toLong().toBigDecimal()
                }
                val address = bdkWallet.revealNextAddress(KeychainKind.EXTERNAL)
                println("Send Signet coins to address ${address.address} (address generated at index ${address.index})")
                bdkWallet.persist(connection)
                return walletRepository.save(bitcoinWallet).toDto()
        }
        // Get a list of a user's wallet
        fun getWalletForUser(userId: Long): WalletDto {
                val user = userService.findById(userId)
                if (user == null) {
                        throw IllegalArgumentException("No user with id = $userId")
                }
                val bitcoinWallet = walletRepository.findByUserId(userId)
                if (bitcoinWallet == null) {
                        throw IllegalArgumentException(
                                "User with id = $userId doesn't have a wallet"
                        )
                }
                val PERSISTENCE_FILE_PATH: String = "user${userId}Wallet.sqlite"
                val persistenceExists = File(PERSISTENCE_FILE_PATH).exists()
                val connection = Connection(PERSISTENCE_FILE_PATH)
                
                val bdkWallet =
                        if (persistenceExists) {
                                println("Loading up existing wallet")
                                val externalDescriptor = Descriptor(bitcoinWallet.externalDescriptor, bitcoinNetwork)
                                val internalDescriptor = Descriptor(bitcoinWallet.internalDescriptor, bitcoinNetwork)
                                println("externalDescriptor: " + externalDescriptor.toStringWithSecret())
                                println("internalDescriptor: " + internalDescriptor.toStringWithSecret())
                                Wallet.load(
                                        descriptor = externalDescriptor,
                                        changeDescriptor = internalDescriptor,
                                        connection = connection
                                )
                        } else {
                                throw IllegalArgumentException(
                                        "No wallet for user with id = $userId"
                                )
                        }

                println("bababooey: " + bdkWallet.listUnusedAddresses(KeychainKind.EXTERNAL))
                val esploraClient: EsploraClient = EsploraClient(ESPLORA_URL)
                val syncRequest = bdkWallet.startSyncWithRevealedSpks().build()
                val update = esploraClient.sync(request = syncRequest, parallelRequests = 1uL)
                bdkWallet.applyUpdate(update)
                bdkWallet.persist(connection)
                val balance = bdkWallet.balance().total.toSat()
                println("Balance: $balance")
                if (balance.toLong().toBigDecimal() != bitcoinWallet.balance) {
                        bitcoinWallet.balance = balance.toLong().toBigDecimal()
                        walletRepository.save(bitcoinWallet)
                }
                return bitcoinWallet.toDto()
        }
        // Update a user's wallet balance for a specific network
        // fun updateWalletBalance(
        //         userId: Long,
        //         updateWalletBalanceDto: UpdateWalletBalanceDto
        // ): WalletDto {
        //         var bitcoinWallet = walletRepository.findByUserId(userId)
        //         if (bitcoinWallet == null) {
        //                 throw IllegalArgumentException(
        //                         "User with id = $userId doesn't have a wallet"
        //                 )
        //         }
        //         bitcoinWallet.balance = updateWalletBalanceDto.balance
        //         return walletRepository.save(bitcoinWallet).toDto()
        // }
        // Delete a wallet by wallet ID
        fun deleteWallet(userId: Long) {
                val bitcoinWallet = walletRepository.findByUserId(userId)
                if (bitcoinWallet == null) {
                        throw IllegalArgumentException(
                                "User with id = $userId doesn't have a wallet"
                        )
                }
                walletRepository.delete(bitcoinWallet)
        }
        // Send a transaction
        fun sendTransaction(userId: Long, sendTransactionDto: SendTransactionDto): WalletDto {
                val addressString = sendTransactionDto.addressString
                val amount = sendTransactionDto.amount
                val recipientAddress: Address = Address(address = addressString, network = bitcoinNetwork)
                val user = userService.findById(userId)
                if (user == null) {
                        throw IllegalArgumentException("No user with id = $userId")
                }
                val bitcoinWallet = walletRepository.findByUserId(userId)
                if (bitcoinWallet == null) {
                        throw IllegalArgumentException(
                                "User with id = $userId doesn't have a wallet"
                        )
                }
                val PERSISTENCE_FILE_PATH: String = "user${userId}Wallet.sqlite"
                val persistenceExists = File(PERSISTENCE_FILE_PATH).exists()
                val connection = Connection(PERSISTENCE_FILE_PATH)
                
                val bdkWallet =
                        if (persistenceExists) {
                                println("Loading up existing wallet")
                                val externalDescriptor = Descriptor(bitcoinWallet.externalDescriptor, bitcoinNetwork)
                                val internalDescriptor = Descriptor(bitcoinWallet.internalDescriptor, bitcoinNetwork)
                                println("externalDescriptor: " + externalDescriptor.toStringWithSecret())
                                println("internalDescriptor: " + internalDescriptor.toStringWithSecret())
                                Wallet.load(
                                        descriptor = externalDescriptor,
                                        changeDescriptor = internalDescriptor,
                                        connection = connection
                                )
                        } else {
                                throw IllegalArgumentException(
                                        "No wallet for user with id = $userId"
                                )
                        }
                val psbt: Psbt = TxBuilder()
                                .addRecipient(script = recipientAddress.scriptPubkey(), amount = Amount.fromSat(satoshi = amount))
                                .feeRate(FeeRate.fromSatPerVb(7uL))
                                .finish(bdkWallet)

                                bdkWallet.sign(psbt)
                val esploraClient: EsploraClient = EsploraClient(ESPLORA_URL)
                val tx: Transaction = psbt.extractTx()
                esploraClient.broadcast(tx)
                println("Transaction broadcast successfully! Txid: ${tx.computeTxid()}")
                val syncRequest = bdkWallet.startSyncWithRevealedSpks().build()
                val update = esploraClient.sync(request = syncRequest, parallelRequests = 1uL)
                bdkWallet.applyUpdate(update)
                bdkWallet.persist(connection)
                val balance = bdkWallet.balance().total.toSat()
                println("Balance: $balance")
                if (balance.toLong().toBigDecimal() != bitcoinWallet.balance) {
                        bitcoinWallet.balance = balance.toLong().toBigDecimal()
                        walletRepository.save(bitcoinWallet)
                }
                return bitcoinWallet.toDto()
        }
        
}
