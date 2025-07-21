package com.evolvdefi.edefi

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {

    @GetMapping("/hello)
    fun sayHello(): String {
        return "Hello, world!"
    }
    
}