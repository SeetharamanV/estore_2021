package com.seetharamanv.estores.repositories

import com.seetharamanv.estores.entities.Item
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository

interface ItemsRepository : PagingAndSortingRepository<Item, Long> {
    fun findBy(pageRequest: Pageable): Page<Item>
}