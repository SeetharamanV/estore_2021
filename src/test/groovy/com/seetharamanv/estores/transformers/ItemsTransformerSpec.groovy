package com.seetharamanv.estores.transformers

import com.seetharamanv.estores.AbstractSpecification
import com.seetharamanv.estores.models.ItemRequest

class ItemsTransformerSpec extends AbstractSpecification {
    def itemsTransformer = new ItemsTransformer()
    def "Transform ItemRequest to Item."() {
        given:
        def itemRequest = easyRandom.nextObject(ItemRequest)

        when:
        def result = itemsTransformer.transformToItem(itemRequest)

        then:
        result.id == 0
        result.name == itemRequest.name
        result.description == itemRequest.description
        result.brand == itemRequest.brand
        result.category == itemRequest.category
        result.barcode == itemRequest.barcode
        result.price == itemRequest.price

    }
}
