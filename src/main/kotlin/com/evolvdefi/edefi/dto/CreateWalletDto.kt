package com.evolvdefi.edefi.dto

import com.evolvdefi.edefi.model.BitcoinWallet
// import com.evolvdefi.edefi.model.User

data class CreateWalletDto(
    val userId: Long, 
    val network: String
)
