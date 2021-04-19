package com.seetharamanv.estores

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.BootstrapWith
import spock.lang.Specification

@BootstrapWith(SpringBootTestContextBootstrapper)
@SpringBootTest(classes = [EstoresApplication])
@ActiveProfiles(profiles = 'integration')
@AutoConfigureMockMvc
abstract class AbstractIntegrationSpecification extends Specification {
    @Autowired
    ObjectMapper objectMapper
}
