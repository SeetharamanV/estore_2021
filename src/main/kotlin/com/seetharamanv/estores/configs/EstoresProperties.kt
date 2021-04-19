package com.seetharamanv.estores.configs

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "estores")
data class EstoresProperties(
    val apiKey: String = "",
    val rootApiUrl: String = "https://api.other.com/"
)
