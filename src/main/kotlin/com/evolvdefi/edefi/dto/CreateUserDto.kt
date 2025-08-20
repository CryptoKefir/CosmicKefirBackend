package com.evolvdefi.edefi.dto

import com.evolvdefi.edefi.model.User

data class CreateUserDto(
    val username: String,
    val email: String,
    val password: String
)

fun CreateUserDto.toEntity(): User {
    return User(
        username = this.username,
        email = this.email,
        password = this.password
    )
}