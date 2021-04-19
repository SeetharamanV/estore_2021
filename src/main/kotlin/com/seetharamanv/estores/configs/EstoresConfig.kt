package com.seetharamanv.estores.configs

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.seetharamanv.estores")
class EstoresConfig
