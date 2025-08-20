package com.evolvdefi.edefi.controller

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import com.evolvdefi.edefi.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.http.ResponseEntity
import com.evolvdefi.edefi.model.User
import com.evolvdefi.edefi.dto.CreateUserDto
import com.evolvdefi.edefi.dto.UserDto
import com.evolvdefi.edefi.dto.UpdateUserDto
import org.springframework.web.bind.annotation.CrossOrigin

// @CrossOrigin(origins = ["http://10.0.3.188:3000"], allowCredentials = "true")
@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {
    @GetMapping("/email/{email}")
    fun getUserByEmail(@PathVariable email: String): ResponseEntity<UserDto> {
        val user = userService.getUserByEmail(email)
        return if (user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    @GetMapping("/all")
    fun getAllUsers(): ResponseEntity<List<UserDto>> {
        val users = userService.getAllUsers()
        return ResponseEntity.ok(users)
    }
    @PutMapping("/update/{userId}")
    fun updateUserbyId(@RequestBody dto: UpdateUserDto): ResponseEntity<UserDto> {
        val user = userService.updateUser(dto)
        return ResponseEntity.ok(user)
    }
    @DeleteMapping("/delete/{userId}")
    fun deleteUser(@PathVariable userId: String): ResponseEntity<UserDto> {
        val user = userService.deleteUser(userId.toLong())
        return ResponseEntity.ok(user)
    }
}