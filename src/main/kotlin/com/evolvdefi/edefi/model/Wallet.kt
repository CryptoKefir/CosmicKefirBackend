package com.evolvdefi.edefi.model

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Column
import jakarta.persistence.Version
import java.math.BigDecimal


@Entity
@Table(name = "wallet")
data class Wallet(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var userId: Long,

    @Column(nullable = false)
    var balance: BigDecimal = BigDecimal.ZERO,

    @Column(nullable = false)
    var currency: String = "USD"
)