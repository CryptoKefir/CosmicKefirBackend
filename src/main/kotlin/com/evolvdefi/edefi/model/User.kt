package com.evolvdefi.edefi.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "users",
    uniqueConstraints = [UniqueConstraint(columnNames = ["email"])])
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    var name: String,
    var email: String,
    var password: String,
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val wallets: MutableList<Wallet> = mutableListOf()
)