package com.evolvdefi.edefi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration::class])
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}
