package com.evolvdefi.edefi.dto

// DTO for transferring user data

data class UserDto(
    val id: Long,
    val name: String,
    val email: String
)

fun com.evolvdefi.edefi.model.User.toDto() = UserDto(id, name, email)

