package com.evolvdefi.edefi.controller

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import com.evolvdefi.edefi.service.UserService
import com.evolvdefi.edefi.dto.CreateUserDto
import com.evolvdefi.edefi.dto.UserDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {
    @PostMapping("/")
    fun createUser(@RequestBody dto: CreateUserDto): UserDto {
        return userService.createUser(dto)
    }
    // @GetMapping("/users/{id}")
    // fun getUserById(@PathVariable id: Long): UserDto {
    //     return userService.getUserById(id)
    // }
    @GetMapping("/email/{email}")
    fun getUserByEmail(@PathVariable email: String): ResponseEntity<UserDto> {
        val userDto = userService.getUserByEmail(email)
        return if (userDto != null) {
            ResponseEntity.ok(userDto)
        } else {
            ResponseEntity.notFound().build()
    }
    }
}