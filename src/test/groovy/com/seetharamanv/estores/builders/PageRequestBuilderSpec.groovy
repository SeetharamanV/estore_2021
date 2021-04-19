package com.seetharamanv.estores.builders

import com.seetharamanv.estores.AbstractSpecification

class PageRequestBuilderSpec extends AbstractSpecification {
    def pageRequestBuilder = new PageRequestBuilder()
    def "Create Pageable: Sort Ascending."() {
        when:
        def result = pageRequestBuilder.build(1, 1, "price", true)

        then:
        result.pageNumber == 1
        result.pageSize == 1
        result.sort.sort().get(0).ascending
        result.sort.sort().get(0).properties.get("property") == "price"
    }
    def "Create Pageable: Sort Descending."() {
        when:
        def result = pageRequestBuilder.build(1, 1, "price", false)

        then:
        result.pageNumber == 1
        result.pageSize == 1
        result.sort.sort().get(0).descending
        result.sort.sort().get(0).properties.get("property") == "price"
    }
}
