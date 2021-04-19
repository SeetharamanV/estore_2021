package com.seetharamanv.estores

import com.seetharamanv.estores.configs.EstoresProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(EstoresProperties::class)
class EstoresApplication

fun main(args: Array<String>) {
    runApplication<EstoresApplication>(*args)
}
