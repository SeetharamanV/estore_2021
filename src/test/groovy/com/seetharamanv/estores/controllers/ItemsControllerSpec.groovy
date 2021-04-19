package com.seetharamanv.estores.controllers

import com.seetharamanv.estores.AbstractSpecification
import com.seetharamanv.estores.entities.Item
import com.seetharamanv.estores.models.ItemRequest
import com.seetharamanv.estores.services.ItemsService
import org.springframework.data.domain.PageImpl
import org.springframework.http.HttpStatus

class ItemsControllerSpec extends AbstractSpecification {
    def itemsService = Mock(ItemsService)
    def itemsController = new ItemsController(itemsService)
    def "Items Controller test - get all items."() {
        given:
        def item = GroovyMock(Item)
        def page = new PageImpl<Item>([item])

        when:
        def result = itemsController.getAllItems(1, 1, "price", true)

        then:
        1 * itemsService.getItemsAndSortByParam(0, 1, "price", true) >> page

        result == page

    }
    def "Items Controller test - create item."() {
        given:
        def itemRequest = GroovyMock(ItemRequest)
        def item = GroovyMock(Item)

        when:
        def result = itemsController.createItem(itemRequest)

        then:
        1 * itemsService.createItem(itemRequest) >> item

        result == item

    }
    def "Items Controller test - update item."() {
        given:
        def item = GroovyMock(Item)

        when:
        def result = itemsController.updateItem(item, 21)

        then:
        1 * itemsService.updateItem(item, 21) >> item

        result == item

    }
    def "Items Controller test - get item by id."() {
        given:
        def item = GroovyMock(Item)

        when:
        def result = itemsController.getItemById(21)

        then:
        1 * itemsService.getItemById(21) >> item

        result == item

    }
    def "Items Controller test - delete item by id."() {
        given:
        def item = GroovyMock(Item)

        when:
        itemsController.deleteItemById(21)

        then:
        1 * itemsService.deleteItem(21) >> {}

        noExceptionThrown()

    }
}
