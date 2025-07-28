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
@Table(name = "wallet",
        uniqueConstraints = [UniqueConstraint(columnNames = ["userId", "currency"])])
class Wallet(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    var user: User,
    @Column(nullable = false)
    var currency: String = "USD",
    @Column(nullable = false)
    var balance: BigDecimal = BigDecimal.ZERO
)
