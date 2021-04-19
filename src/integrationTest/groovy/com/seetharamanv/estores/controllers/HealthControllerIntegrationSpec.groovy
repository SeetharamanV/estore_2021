package com.seetharamanv.estores.controllers

import com.seetharamanv.estores.AbstractRestIntegrationSpecification
import org.springframework.test.web.servlet.MvcResult

class HealthControllerIntegrationSpec extends AbstractRestIntegrationSpecification {

    def "Test Health Controller"() {
        when:  "Get Requisition Options"
        MvcResult result = mockGet("/health").andReturn()

        then:
        result.response.status == 200
    }
}
