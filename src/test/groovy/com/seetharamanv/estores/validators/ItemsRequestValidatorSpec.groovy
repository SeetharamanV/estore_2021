package com.seetharamanv.estores.validators

import com.seetharamanv.estores.AbstractSpecification
import com.seetharamanv.estores.entities.Item
import com.seetharamanv.estores.exceptions.ItemBadRequestException
import com.seetharamanv.estores.models.ItemRequest

class ItemsRequestValidatorSpec extends AbstractSpecification {
    def itemRequestValidator = new ItemsRequestValidator()
    def "Validate Item request price - above zero"() {
        when:  "price is 1.0"
        itemRequestValidator.validate(new ItemRequest("","","","","",1.0))

        then:
        noExceptionThrown()
    }

    def "Validate Item price - above zero"() {
        when:  "price is 0.01"
        itemRequestValidator.validate(new Item(1, "","","","","",0.01))

        then:
        noExceptionThrown()
    }

    def "Validate Item request price - zero"() {
        when:  "price is 0.0"
        itemRequestValidator.validate(new ItemRequest("","","","","",0.0))

        then:
        ItemBadRequestException caught = thrown(ItemBadRequestException.class)
        caught.message == "Item price attempting to save: [0.0], should be greater than zero."
    }

    def "Validate Item price -  zero"() {
        when:  "price is 0.0"
        itemRequestValidator.validate(new Item(1, "","","","","",0.0))

        then:
        ItemBadRequestException caught = thrown(ItemBadRequestException.class)
        caught.message == "Item price attempting to save: [0.0], should be greater than zero."
    }

    def "Validate Item request price - less than zero"() {
        when:  "price is -1.0"
        itemRequestValidator.validate(new ItemRequest("","","","","",-1.0))

        then:
        ItemBadRequestException caught = thrown(ItemBadRequestException.class)
        caught.message == "Item price attempting to save: [-1.0], should be greater than zero."
    }

    def "Validate Item price - less than zero"() {
        when:  "price is -1.0"
        itemRequestValidator.validate(new Item(1, "","","","","",-1.0))

        then:
        ItemBadRequestException caught = thrown(ItemBadRequestException.class)
        caught.message == "Item price attempting to save: [-1.0], should be greater than zero."
    }
}
