package com.evolvdefi.edefi.controller

import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import com.evolvdefi.edefi.service.TransactionService
import com.evolvdefi.edefi.model.TransactionInfo
import java.math.BigDecimal

@RestController
@RequestMapping("/api/transactions")
class TransactionController(private val transactionService: TransactionService) {
    
    @GetMapping("/address")
    fun getNewAddress(
        @RequestParam userId: Long,
        @RequestParam network: String
    ): ResponseEntity<String> {
        return ResponseEntity.ok(transactionService.getNewAddress(userId, network))
    }
    
    @GetMapping("/balance")
    fun getBalance(
        @RequestParam userId: Long,
        @RequestParam network: String
    ): ResponseEntity<BigDecimal> {
        return ResponseEntity.ok(transactionService.getBalance(userId, network))
    }
    
    @PostMapping("/send")
    fun sendTransaction(
        @RequestParam userId: Long,
        @RequestParam network: String,
        @RequestParam toAddress: String,
        @RequestParam amount: BigDecimal
    ): ResponseEntity<String> {
        val txId = transactionService.sendTransaction(userId, network, toAddress, amount)
        return ResponseEntity.ok(txId)
    }
    
    @GetMapping("/history")
    fun getTransactionHistory(
        @RequestParam userId: Long,
        @RequestParam network: String
    ): ResponseEntity<List<TransactionInfo>> {
        return ResponseEntity.ok(transactionService.getTransactionHistory(userId, network))
    }
} 