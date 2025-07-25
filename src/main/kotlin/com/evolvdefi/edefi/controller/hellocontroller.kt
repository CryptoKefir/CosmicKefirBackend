package com.evolvdefi.edefi

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import com.evolvdefi.edefi.service.WalletService
import com.evolvdefi.edefi.service.UserService
import com.evolvdefi.edefi.model.Wallet
import com.evolvdefi.edefi.model.User

@RestController
class HelloController(private val walletService: WalletService, private val userService: UserService) {

    @GetMapping("/hello")
    fun sayHello(): String {
        return "Welcome to Evolv Defi!"
    }

    @GetMapping("/ooga")
    fun getWalletsForUser(): List<Wallet> {
        return walletService.getWalletsForUser(0)
    }
}