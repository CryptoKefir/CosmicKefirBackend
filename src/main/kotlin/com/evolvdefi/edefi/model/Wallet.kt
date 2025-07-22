package com.evolvdefi.edefi.model

@Entity
data class Wallet(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val address: String,
    val privateKey: String,
    val publicKey: String,
    val balance: Double = 0.0
    @OneToOne
    val user: User? = null
)