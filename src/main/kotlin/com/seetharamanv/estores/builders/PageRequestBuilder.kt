package com.seetharamanv.estores.builders

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class PageRequestBuilder {
    fun build(page: Int, size: Int, sortBy: String, isAscending: Boolean): Pageable {
        val sort = if (isAscending) {
            Sort.by(sortBy).ascending()
        } else {
            Sort.by(sortBy).descending()
        }
        return PageRequest.of(page, size, sort)
    }
}