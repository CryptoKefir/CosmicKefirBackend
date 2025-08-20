package com.evolvdefi.edefi.dto

import com.evolvdefi.edefi.model.User

data class UpdateUserDto(
    val id: Long,
    val username: String,
    val email: String,
    val password: String
)

