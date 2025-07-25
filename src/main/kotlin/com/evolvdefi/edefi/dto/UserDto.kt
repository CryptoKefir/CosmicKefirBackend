package com.evolvdefi.edefi.dto

import com.evolvdefi.edefi.model.User

data class UserDto(
    val id: Long,
    val name: String,
    val email: String
)

fun User.toDto(): UserDto {
    return UserDto(
        id = this.id,
        name = this.name,
        email = this.email
    )
}