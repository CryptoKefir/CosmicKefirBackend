package com.evolvdefi.edefi.model

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Column
import jakarta.persistence.Version
import jakarta.persistence.UniqueConstraint
import java.math.BigDecimal
import jakarta.persistence.ManyToOne
import jakarta.persistence.JoinColumn
import jakarta.persistence.FetchType
import com.evolvdefi.edefi.model.User

@Entity
@Table(name = "bitcoinWallet",
        uniqueConstraints = [UniqueConstraint(columnNames = ["userId", "network"])])
class BitcoinWallet(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    val user: User,
    @Column(nullable = false)
    val network: String,
    @Column(nullable = false)
    var balance: BigDecimal = BigDecimal.ZERO,
    val externalDescriptor: String,
    val internalDescriptor: String,
    val status: String
)
