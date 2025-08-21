package com.evolvdefi.edefi.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import com.evolvdefi.edefi.service.BitcoinWalletService
import com.evolvdefi.edefi.service.UserService
import com.evolvdefi.edefi.model.User
import com.evolvdefi.edefi.dto.CreateWalletDto
import com.evolvdefi.edefi.dto.WalletDto
import com.evolvdefi.edefi.dto.UpdateWalletBalanceDto
import com.evolvdefi.edefi.dto.SendTransactionDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin

// @CrossOrigin(origins = ["http://10.0.3.188:3000"], allowCredentials = "true")
@RestController
@RequestMapping("api/wallet")
class WalletController(private val bitcoinWalletService: BitcoinWalletService) {
    @PostMapping("/add")
    fun createWalletForUser(@RequestBody createWalletDto: CreateWalletDto): WalletDto {
        return bitcoinWalletService.createWalletForUser(createWalletDto)
    }
    @GetMapping("/getWallet/{userId}")
    fun getWalletsForUser(@PathVariable userId: Long): WalletDto {
        return bitcoinWalletService.getWalletForUser(userId)
    }
    // @PutMapping("/updateBalance/{userId}/{currency}")
    // fun updateWalletBalance(@PathVariable userId: Long, @RequestBody updateWalletBalanceDto: UpdateWalletBalanceDto): WalletDto {
    //     return bitcoinWalletService.updateWalletBalance(userId, updateWalletBalanceDto)
    // }
    @DeleteMapping("/delete/{userId}")
    fun deleteWallet(@PathVariable userId: Long): ResponseEntity<Void> {
        bitcoinWalletService.deleteWallet(userId)
        return ResponseEntity.noContent().build()
    }
    @PostMapping("/sendTransaction/user{userId}")
    fun sendTransaction(@PathVariable userId: Long, @RequestBody sendTransactionDto: SendTransactionDto): WalletDto {
        return bitcoinWalletService.sendTransaction(userId, sendTransactionDto)
    }
}