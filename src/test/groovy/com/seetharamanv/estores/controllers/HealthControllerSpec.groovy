package com.seetharamanv.estores.controllers

import com.seetharamanv.estores.AbstractSpecification
import org.springframework.http.HttpStatus

class HealthControllerSpec extends AbstractSpecification {
    def "HealthController test"() {
        given:
        def healthController = new HealthController()

        when:
        def result = healthController.getHealth()

        then:
        result == HttpStatus.OK

    }
}
