package com.evolvdefi.edefi.dto

import com.evolvdefi.edefi.model.User

data class CreateUserDto(
    val name: String,
    val email: String
)
fun CreateUserDto.toEntity() = User(name = name, email = email)