package com.seetharamanv.estores.controllers

import com.seetharamanv.estores.entities.Item
import com.seetharamanv.estores.models.ItemRequest
import com.seetharamanv.estores.services.ItemsService
import io.micrometer.core.annotation.Timed
import io.swagger.annotations.ApiParam
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["v1/items"], produces = [MediaType.APPLICATION_JSON_VALUE])
class ItemsController(private val itemsService: ItemsService) {
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    @Timed(value = "items_get")
    fun getAllItems(
            @ApiParam(name = "page", type = "Integer", value = "A Non-Zero Non-Negative number.", example = "1") @RequestParam(defaultValue = "1") page: Int,
            @ApiParam(name = "size", type = "Integer", value = "A Non-Zero Non-Negative number.", example = "5") @RequestParam(defaultValue = "5") size: Int,
            @ApiParam(name = "sortByParam", type = "String", value = "values from this list [id, name, description, brand, category, barcode, price]", example = "id") @RequestParam(required = false, defaultValue = "id") sortByParam: String,
            @ApiParam(name = "sortAscending", type = "Boolean", value = "Ascending set true, Descending set false", example = "true") @RequestParam(required = false, defaultValue = "true") sortAscending: Boolean
    ) = itemsService.getItemsAndSortByParam(page.minus(1), size, sortByParam, sortAscending)

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @Timed(value = "item_create")
    fun createItem(@RequestBody itemRequest: ItemRequest) = itemsService.createItem(itemRequest)

    @PutMapping("/{item_id}")
    @ResponseStatus(value = HttpStatus.OK)
    @Timed(value = "item_create")
    fun updateItem(@RequestBody item: Item, @PathVariable(value = "item_id") itemId: Long) = itemsService.updateItem(item, itemId)

    @GetMapping("/{item_id}")
    @ResponseStatus(value = HttpStatus.OK)
    @Timed(value = "item_get_by_id")
    fun getItemById(@PathVariable(value = "item_id") itemId: Long) = itemsService.getItemById(itemId)

    @DeleteMapping("/{item_id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Timed(value = "item_delete")
    fun deleteItemById(@PathVariable(value = "item_id") itemId: Long) = itemsService.deleteItem(itemId)
}
