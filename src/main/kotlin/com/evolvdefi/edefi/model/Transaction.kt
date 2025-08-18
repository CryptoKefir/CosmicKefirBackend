package com.evolvdefi.edefi.model

import java.math.BigDecimal
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType

data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val txid: String,
    val amount: BigDecimal,
    val fee: BigDecimal?,
    val timestamp: Long,
    val senderaddress: String,
    val receiveraddress: String,
    val network: String,
    val confirmed: Boolean
)