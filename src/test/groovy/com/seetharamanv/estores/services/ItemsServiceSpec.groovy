package com.seetharamanv.estores.services

import com.seetharamanv.estores.AbstractSpecification
import com.seetharamanv.estores.builders.PageRequestBuilder
import com.seetharamanv.estores.entities.Item
import com.seetharamanv.estores.exceptions.EstoresInternalException
import com.seetharamanv.estores.exceptions.EstoresPropertyReferenceException
import com.seetharamanv.estores.exceptions.ItemBadRequestException
import com.seetharamanv.estores.exceptions.ItemNotFoundException
import com.seetharamanv.estores.models.ItemRequest
import com.seetharamanv.estores.repositories.ItemsRepository
import com.seetharamanv.estores.transformers.ItemsTransformer
import com.seetharamanv.estores.validators.ItemsRequestValidator
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.mapping.PropertyReferenceException

class ItemsServiceSpec extends AbstractSpecification {
    def itemsRepository = Mock(ItemsRepository)
    def pageRequestBuilder = Mock(PageRequestBuilder)
    def itemRequestValidator = Mock(ItemsRequestValidator)
    def itemsTransformer = Mock(ItemsTransformer)
    def itemsService = new com.seetharamanv.estores.services.ItemsService(pageRequestBuilder, itemsRepository, itemRequestValidator, itemsTransformer)

    def "Get Items And Sort By Param - Sort by unknown column - throw exception."() {
        given:
        def pageable = GroovyMock(Pageable)

        when:  "sort by unknown column"
        itemsService.getItemsAndSortByParam(1, 1, "unknown", true)

        then:
        1 * pageRequestBuilder.build(1, 1, "unknown", true) >> pageable
        1 * itemsRepository.findBy(pageable) >> {throw GroovyMock(PropertyReferenceException)}
        EstoresPropertyReferenceException caught = thrown(EstoresPropertyReferenceException.class)
        caught.message == "Invalid property [unknown] passed, for item search."

    }
    def "Get Items And Sort By Param - Internal Error - throw exception."() {
        given:
        def pageable = GroovyMock(Pageable)

        when:  "regular call."
        itemsService.getItemsAndSortByParam(1, 1, "price", true)

        then:
        1 * pageRequestBuilder.build(1, 1, "price", true) >> pageable
        1 * itemsRepository.findBy(pageable) >> {throw GroovyMock(Exception)}
        EstoresInternalException caught = thrown(EstoresInternalException.class)
        caught.message == "Threw an internal server exception, while searching."

    }
    def "Get Items And Sort By Param - Happy Path sort ascending."() {
        given:
        def pageable = GroovyMock(Pageable)
        def itemList = [easyRandom.nextObject(Item)]
        def page = new PageImpl<Item>(itemList)

        when:  "regular call."
        def result = itemsService.getItemsAndSortByParam(1, 1, "price", true)

        then:
        1 * pageRequestBuilder.build(1, 1, "price", true) >> pageable
        1 * itemsRepository.findBy(pageable) >> page

        result == page
        noExceptionThrown()
    }
    def "Get Items And Sort By Param - Happy Path sort descending."() {
        given:
        def pageable = GroovyMock(Pageable)
        def itemList = [easyRandom.nextObject(Item)]
        def page = new PageImpl<Item>(itemList)

        when:  "regular call."
        def result = itemsService.getItemsAndSortByParam(1, 1, "price", false)

        then:
        1 * pageRequestBuilder.build(1, 1, "price", false) >> pageable
        1 * itemsRepository.findBy(pageable) >> page

        result == page
        noExceptionThrown()
    }

    def "Get Item - Happy Path "() {
        given:
        def itemId = 21
        def item = GroovyMock(Item)

        when: "regular call."
        def result = itemsService.getItemById(itemId)

        then:
        1 * itemsRepository.findById(itemId) >> new Optional(item)

        result == item
    }
    def "Get Item - Not found "() {
        given:
        def itemId = 21

        when: "regular call."
        itemsService.getItemById(itemId)

        then:
        1 * itemsRepository.findById(itemId) >> { throw GroovyMock(NoSuchElementException)}

        ItemNotFoundException caught = thrown(ItemNotFoundException.class)
        caught.message == "Item with id: [$itemId] doesn't exists."

    }
    def "Get Item - Internal Exception "() {
        given:
        def itemId = 21

        when: "regular call."
        itemsService.getItemById(itemId)

        then:
        1 * itemsRepository.findById(itemId) >> { throw GroovyMock(Exception)}

        EstoresInternalException caught = thrown(EstoresInternalException.class)
        caught.message == "Item with id: [$itemId] threw an internal server exception."

    }

    def "Create Item - Happy Path "() {
        given:
        def itemRequest = GroovyMock(ItemRequest)
        def item = GroovyMock(Item)

        when:  "regular call."
        def result = itemsService.createItem(itemRequest)

        then:
        1 * itemRequestValidator.validate(itemRequest) >> {}
        1 * itemsTransformer.transformToItem(itemRequest) >> item
        1 * itemsRepository.save(item) >> item

        result == item
        noExceptionThrown()
    }
    def "Create Item - Data Integrity Violation "() {
        given:
        def itemRequest = GroovyMock(ItemRequest)
        def item = easyRandom.nextObject(Item)

        when:  "regular call."
        itemsService.createItem(itemRequest)

        then:
        1 * itemRequestValidator.validate(itemRequest) >> {}
        1 * itemsTransformer.transformToItem(itemRequest) >> item
        1 * itemsRepository.save(item) >> { throw GroovyMock(DataIntegrityViolationException)}

        ItemBadRequestException caught = thrown(ItemBadRequestException.class)
        caught.message == "Item with name: [${item.name}] already exists."

    }
    def "Create Item - Internal Error "() {
        given:
        def itemRequest = GroovyMock(ItemRequest)
        def item = easyRandom.nextObject(Item)

        when:  "regular call."
        itemsService.createItem(itemRequest)

        then:
        1 * itemRequestValidator.validate(itemRequest) >> {}
        1 * itemsTransformer.transformToItem(itemRequest) >> item
        1 * itemsRepository.save(item) >> { throw GroovyMock(Exception)}

        EstoresInternalException caught = thrown(EstoresInternalException.class)
        caught.message == "Threw an internal server exception, while saving item [${item.name}]."

    }

    def "Update Item - Happy Path "() {
        given:
        def item = easyRandom.nextObject(Item)

        when:  "regular call."
        def result = itemsService.updateItem(item, item.id)

        then:
        1 * itemRequestValidator.validate(item) >> {}
        1 * itemsRepository.findById(item.id) >> new Optional(GroovyMock(Item))
        1 * itemsRepository.save(item) >> item

        result == item
    }
    def "Update Item - values same as existing data "() {
        given:
        def item = easyRandom.nextObject(Item)

        when:  "regular call."
        def result = itemsService.updateItem(item, item.id)

        then:
        1 * itemRequestValidator.validate(item) >> {}
        1 * itemsRepository.findById(item.id) >> new Optional(item)
        0 * itemsRepository.save(item) >> item

        result == item
    }
    def "Update Item - item id's don't match "() {
        given:
        def item = easyRandom.nextObject(Item)

        when:  "regular call."
        itemsService.updateItem(item, 21)

        then:
        0 * itemRequestValidator.validate(item) >> {}
        0 * itemsRepository.findById(item.id) >> new Optional(item)
        0 * itemsRepository.save(item) >> item

        ItemBadRequestException caught = thrown(ItemBadRequestException.class)
        caught.message == "Item id mismatch: body id - [${item.id}] and path id - [21]."

    }

    def "Delete Item - Happy Path "() {
        given:
        def itemId = 21

        when: "regular call."
        itemsService.deleteItem(itemId)

        then:
        1 * itemsRepository.deleteById(itemId) >> {}

        noExceptionThrown()

    }
    def "Delete Item - Not found "() {
        given:
        def itemId = 21

        when: "regular call."
        itemsService.deleteItem(itemId)

        then:
        1 * itemsRepository.deleteById(itemId) >> { throw GroovyMock(EmptyResultDataAccessException)}

        ItemNotFoundException caught = thrown(ItemNotFoundException.class)
        caught.message == "Cannot delete Item with id: [$itemId], item not found."

    }
    def "Delete Item - Internal Exception "() {
        given:
        def itemId = 21

        when: "regular call."
        itemsService.deleteItem(itemId)

        then:
        1 * itemsRepository.deleteById(itemId) >> { throw GroovyMock(Exception)}

        EstoresInternalException caught = thrown(EstoresInternalException.class)
        caught.message == "Item with id: [$itemId] threw an internal server exception."

    }
}
