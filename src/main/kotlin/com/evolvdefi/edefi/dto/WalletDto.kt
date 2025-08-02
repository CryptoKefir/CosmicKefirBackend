package com.evolvdefi.edefi.dto

import com.evolvdefi.edefi.model.CKWallet
import java.math.BigDecimal

data class WalletDto(
    val id: Long?, 
    val network: String,
    val balance: BigDecimal
)

fun CKWallet.toDto(): WalletDto {
    return WalletDto(
        id = this.id,
        network = this.network,
        balance = this.balance
    )
}