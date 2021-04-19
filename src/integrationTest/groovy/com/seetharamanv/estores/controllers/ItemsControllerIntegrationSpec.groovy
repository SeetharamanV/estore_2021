package com.seetharamanv.estores.controllers

import com.seetharamanv.estores.AbstractRestIntegrationSpecification
import com.seetharamanv.estores.entities.Item
import org.springframework.test.web.servlet.MvcResult

class ItemsControllerIntegrationSpec extends AbstractRestIntegrationSpecification {

    def "Test Items Controller"() {
        when: "Get an item, that doesn't exist."
        MvcResult result = mockGet("/v1/items/90001").andReturn()

        then:
        result.response.status == 404

        when: "Get an item, bad request."
        result = mockGet("/v1/items/ABC").andReturn()

        then:
        result.response.status == 400

        when:  "Create an item."
        result = mockPost("/v1/items",
                """
                {
                  "name": "Test_1",
                  "description": "Integration Test_1",
                  "brand": "Integration",
                  "category": "TEST",
                  "barcode": "QWEQ000101",
                  "price": 10
                }
        """).andReturn()

        then:
        result.response.status == 201
        def createResponse = objectMapper.readValue(result.response.contentAsString, Item.class)
        def itemId = createResponse.id
        itemId != null
        itemId > 0
        createResponse.name == "Test_1"
        createResponse.description == "Integration Test_1"
        createResponse.brand == "Integration"
        createResponse.category == "TEST"
        createResponse.barcode == "QWEQ000101"
        createResponse.price == 10

        when:  "Get an item."
        result = mockGet("/v1/items/$itemId").andReturn()

        then:
        result.response.status == 200
        def getResponse = objectMapper.readValue(result.response.contentAsString, Item.class)
        itemId == getResponse.id
        getResponse.name == "Test_1"
        getResponse.description == "Integration Test_1"
        getResponse.brand == "Integration"
        getResponse.category == "TEST"
        getResponse.barcode == "QWEQ000101"
        getResponse.price == 10

        when:  "Update an item."
        result = mockPut("/v1/items/$itemId",
                """
                {
                  "name": "Updated Test_1",
                  "description": "Integration Test_1",
                  "brand": "Integration",
                  "category": "TEST",
                  "barcode": "QWEQ000101",
                  "price": 9.99,
                  "id": $itemId
                }
        """).andReturn()

        then:
        result.response.status == 200
        def postResponse = objectMapper.readValue(result.response.contentAsString, Item.class)
        itemId == postResponse.id
        postResponse.name == "Updated Test_1"
        postResponse.description == "Integration Test_1"
        postResponse.brand == "Integration"
        postResponse.category == "TEST"
        postResponse.barcode == "QWEQ000101"
        postResponse.price == 9.99

        when:  "Delete an item, by item id."
        result = mockDelete("/v1/items/$itemId").andReturn()

        then:
        result.response.status == 204

        when: "Get an item, that was deleted."
        result = mockGet("/v1/items/$itemId").andReturn()

        then:
        result.response.status == 404
    }
}
