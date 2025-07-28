package com.evolvdefi.edefi.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import com.evolvdefi.edefi.service.WalletService
import com.evolvdefi.edefi.service.UserService
import com.evolvdefi.edefi.model.Wallet
import com.evolvdefi.edefi.model.User
import com.evolvdefi.edefi.dto.CreateWalletDto
import com.evolvdefi.edefi.dto.WalletDto
import com.evolvdefi.edefi.dto.UpdateWalletBalanceDto
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("api/wallet")
class WalletController(private val walletService: WalletService) {
    @PostMapping("/add")
    fun createWalletForUser(@RequestBody createWalletDto: CreateWalletDto): WalletDto {
        return walletService.createWalletForUser(createWalletDto)
    }
    @GetMapping("/getWallets/{userId}")
    fun getWalletsForUser(@PathVariable userId: Long): List<WalletDto> {
        return walletService.getWalletsForUser(userId)
    }
    @PutMapping("/updateBalance/{userId}/{currency}")
    fun updateWalletBalance(@PathVariable userId: Long, @PathVariable currency: String, @RequestBody updateWalletBalanceDto: UpdateWalletBalanceDto): WalletDto {
        return walletService.updateWalletBalance(userId, currency, updateWalletBalanceDto)
    }
    @DeleteMapping("/delete/{id}")
    fun deleteWallet(@PathVariable id: Long): ResponseEntity<Void> {
        walletService.deleteWallet(id)
        return ResponseEntity.noContent().build()
    }
}