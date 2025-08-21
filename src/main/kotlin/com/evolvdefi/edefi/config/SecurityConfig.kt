package com.evolvdefi.edefi.config

import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder =
        SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8()
}