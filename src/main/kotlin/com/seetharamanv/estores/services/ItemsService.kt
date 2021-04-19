package com.seetharamanv.estores.services

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
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mapping.PropertyReferenceException
import org.springframework.stereotype.Service

@Service
class ItemsService(private val pageRequestBuilder: PageRequestBuilder, private val itemsRepository: ItemsRepository, private val itemsRequestValidator: ItemsRequestValidator, val itemsTransformer: ItemsTransformer) {
    fun getItemsAndSortByParam(page: Int, size: Int, sortBy: String, isAscendingOrder: Boolean): Page<Item> {
        try {
            val pageable: Pageable = pageRequestBuilder.build(page, size, sortBy, isAscendingOrder)
            return itemsRepository.findBy(pageable)
        } catch (propertyReferenceException: PropertyReferenceException) {
            throw EstoresPropertyReferenceException("Invalid property [$sortBy] passed, for item search.")
        } catch (ex: Exception) {
            throw EstoresInternalException("Threw an internal server exception, while searching.")
        }
    }

    fun createItem(itemRequest: ItemRequest): Item {
        itemsRequestValidator.validate(itemRequest)
        return saveItem(itemsTransformer.transformToItem(itemRequest))
    }

    fun updateItem(item: Item, itemId: Long): Item {
        if (itemId == item.id) {
            itemsRequestValidator.validate(item)
            val existingItem = getItemById(itemId)
            return if (existingItem != item) {
                saveItem(item)
            } else {
                item // throw ItemUpdateIdenticalToStoredDataException("Item being attempted to update is exactly identical to data previously saved.")
            }
        }
        throw ItemBadRequestException("Item id mismatch: body id - [${item.id}] and path id - [${itemId}].")
    }

    private fun saveItem(item: Item): Item {
        try {
            return itemsRepository.save(item)
        } catch (dataIntegrityViolationException: DataIntegrityViolationException) {
            throw ItemBadRequestException("Item with name: [${item.name}] already exists.")
        } catch (ex: Exception) {
            throw EstoresInternalException("Threw an internal server exception, while saving item [${item.name}].")
        }
    }

    fun getItemById(itemId: Long): Item {
        try {
            return itemsRepository.findById(itemId).get()
        } catch (noSuchElementException: NoSuchElementException) {
            throw ItemNotFoundException("Item with id: [$itemId] doesn't exists.")
        } catch (exception: Exception) {
            throw EstoresInternalException("Item with id: [$itemId] threw an internal server exception.")
        }
    }

    fun deleteItem(itemId: Long) {
        try {
            itemsRepository.deleteById(itemId)
        } catch (emptyResultDataAccessException: EmptyResultDataAccessException) {
            throw ItemNotFoundException("Cannot delete Item with id: [$itemId], item not found.")
        } catch (ex: Exception) {
            throw EstoresInternalException("Item with id: [$itemId] threw an internal server exception.")
        }
    }
}