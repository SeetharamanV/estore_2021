package com.seetharamanv.estores.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["health"], produces = [MediaType.APPLICATION_JSON_VALUE])
class HealthController {

    @GetMapping
    fun getHealth(): HttpStatus {
        return HttpStatus.OK
    }
}
