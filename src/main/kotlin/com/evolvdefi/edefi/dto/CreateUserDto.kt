package com.evolvdefi.edefi.dto

import com.evolvdefi.edefi.model.User

data class CreateUserDto(
    val name: String,
    val email: String,
    val password: String
)

fun CreateUserDto.toEntity(): User {
    return User(
        name = this.name,
        email = this.email,
        password = this.password
    )
}
