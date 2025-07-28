package com.evolvdefi.edefi.dto

import com.evolvdefi.edefi.model.Wallet
import java.math.BigDecimal

data class WalletDto(
    val id: Long?, 
    val currency: String,
    val balance: BigDecimal
)

fun Wallet.toDto(): WalletDto {
    return WalletDto(
        id = this.id,
        currency = this.currency,
        balance = this.balance
    )
}