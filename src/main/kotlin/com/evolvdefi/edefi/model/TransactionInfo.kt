package com.evolvdefi.edefi.model

import java.math.BigDecimal

data class TransactionInfo(
    val txid: String,
    val amount: BigDecimal,
    val fee: BigDecimal?,
    val timestamp: Long,
    val confirmed: Boolean
) 