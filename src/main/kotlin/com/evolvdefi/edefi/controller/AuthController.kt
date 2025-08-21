package com.evolvdefi.edefi.controller

import com.evolvdefi.edefi.dto.CreateUserDto
import com.evolvdefi.edefi.dto.UserDto
import com.evolvdefi.edefi.dto.LoginDto
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.evolvdefi.edefi.service.AuthService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable


// @CrossOrigin(origins = ["http://10.0.3.188:3000"], allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {
    @PostMapping("/add")
    fun createUser(@RequestBody dto: CreateUserDto): UserDto {
        return authService.createUser(dto)
    }
    @PostMapping("/login")
    fun login(@RequestBody dto: LoginDto): UserDto {
        return authService.login(dto)
    }
    @GetMapping("/getallusers")
    fun getAllUsers(): List<UserDto> {
        return authService.getAllUsers()
    }
    @DeleteMapping("/deleteuser/{id}")
    fun deleteUser(@PathVariable id: Long): UserDto {
        return authService.deleteUser(id)
    }
}
