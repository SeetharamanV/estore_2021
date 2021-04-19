package com.seetharamanv.estores.transformers

import com.seetharamanv.estores.entities.Item
import com.seetharamanv.estores.models.ItemRequest
import org.springframework.stereotype.Component

@Component
class ItemsTransformer {
    fun transformToItem(itemRequest: ItemRequest): Item = Item(
                id = 0, name = itemRequest.name, description = itemRequest.description,
                brand = itemRequest.brand, category = itemRequest.category,
                barcode = itemRequest.barcode, price = itemRequest.price
        )
}