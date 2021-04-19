package com.seetharamanv.estores.validators

import com.seetharamanv.estores.entities.Item
import com.seetharamanv.estores.exceptions.ItemBadRequestException
import com.seetharamanv.estores.models.ItemRequest
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class ItemsRequestValidator {
    fun validate (itemRequest: ItemRequest) {
        validatePrice(itemRequest.price)
    }
    fun validate (item: Item) {
        validatePrice(item.price)
    }

    private fun validatePrice(price: Double) {
        if (price <= BigDecimal.ZERO.toDouble()) {
            throw ItemBadRequestException("Item price attempting to save: [${price}], should be greater than zero.")
        }
    }
}