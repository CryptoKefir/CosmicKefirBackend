package com.evolvdefi.edefi.dto

import com.evolvdefi.edefi.model.User

data class UserDto(
    val id: Long,
    val username: String,
    val email: String
)

fun User.toDto(): UserDto {
    return UserDto(
        id = this.id,
        username = this.username.lowercase(),
        email = this.email.lowercase()
    )
}