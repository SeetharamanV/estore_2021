package com.seetharamanv.estores

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put

@AutoConfigureMockMvc
abstract class AbstractRestIntegrationSpecification extends AbstractIntegrationSpecification {
    @Autowired
    MockMvc mockMvc

    protected String AUTH_HEADER_NAME = "X-Header"
    protected String AUTH_HEADER_VALUE = "Token"

    def mockGet(String url) {
        return mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON))
    }

    def mockPost(String url, String postBody) {
        return mockMvc.perform(post(url)
                .content(postBody)
                .contentType(MediaType.APPLICATION_JSON)
                .header(AUTH_HEADER_NAME, AUTH_HEADER_VALUE)
                .accept(MediaType.APPLICATION_JSON))
    }

    def mockPut(String url, String putBody) {
        return mockMvc.perform(put(url)
                .content(putBody)
                .contentType(MediaType.APPLICATION_JSON)
                .header(AUTH_HEADER_NAME, AUTH_HEADER_VALUE)
                .accept(MediaType.APPLICATION_JSON))
    }

    def mockDelete(String url) {
        return mockMvc.perform(delete(url)
                .header(AUTH_HEADER_NAME, AUTH_HEADER_VALUE)
                .accept(MediaType.APPLICATION_JSON))
    }
}
