package com.evolvdefi.edefi.dto

import com.evolvdefi.edefi.model.Wallet

data class CreateWalletDto(
    val userId: Long, 
    val currency: String
)

fun CreateWalletDto.toEntity(): Wallet {
    return Wallet(
        userId = this.userId,
        currency = this.currency
    )
}
