package com.evolvdefi.edefi.dto

import com.evolvdefi.edefi.model.Wallet
// import com.evolvdefi.edefi.model.User

data class CreateWalletDto(
    val userId: Long, 
    val currency: String
)

// fun CreateWalletDto.toEntity(): Wallet {
//     return Wallet(
//         user = User(),
//         currency = this.currency
//     )
// }
