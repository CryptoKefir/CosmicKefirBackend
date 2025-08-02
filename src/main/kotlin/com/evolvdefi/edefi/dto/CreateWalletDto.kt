package com.evolvdefi.edefi.dto

import com.evolvdefi.edefi.model.CKWallet
// import com.evolvdefi.edefi.model.User

data class CreateWalletDto(
    val userId: Long, 
    val network: String
)

// fun CreateWalletDto.toEntity(): Wallet {
//     return Wallet(
//         user = User(),
//         currency = this.currency
//     )
// }
