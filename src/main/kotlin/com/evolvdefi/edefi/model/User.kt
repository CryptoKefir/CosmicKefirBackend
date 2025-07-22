package com.evolvdefi.edefi.model

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val name: String,
    val email: String,
    @OneToOne
    val wallet: Wallet? = null
)
