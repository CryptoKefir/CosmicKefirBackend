package com.evolvdefi.edefi.model
import jakarta.persistence.*
import java.math.BigDecimal


@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val name: String,
    val email: String,
    val password: String
)