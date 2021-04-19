package com.seetharamanv.estores.exceptions

import com.seetharamanv.estores.AbstractSpecification
import org.springframework.http.HttpStatus

import javax.servlet.http.HttpServletRequestWrapper

class AllExceptionsHandlerSpec extends AbstractSpecification {
    def allExceptionsHandler = new AllExceptionsHandler()
    def httpServletRequest = Mock(HttpServletRequestWrapper)
    def "Handle : ItemBadRequestException"() {
        given:
        def ex = easyRandom.nextObject(ItemBadRequestException)
        when:
        def result = allExceptionsHandler.handleItemBadRequestException(ex, httpServletRequest)

        then:
        1 * httpServletRequest.getRequestURI() >> "path"
        result.getStatusCode() == HttpStatus.BAD_REQUEST
        def errorResponse = result.getBody()
        errorResponse.exceptionName == "ItemBadRequestException"
        errorResponse.path == "path"
        errorResponse.httpStatus == HttpStatus.BAD_REQUEST
        errorResponse.httpCode == HttpStatus.BAD_REQUEST.value()
        errorResponse.message != null
        errorResponse.data == null
    }
    def "Handle : ItemNotFoundException"() {
        given:
        def ex = easyRandom.nextObject(ItemNotFoundException)
        when:
        def result = allExceptionsHandler.handleItemNotFoundException(ex, httpServletRequest)

        then:
        1 * httpServletRequest.getRequestURI() >> "path"
        result.getStatusCode() == HttpStatus.NOT_FOUND
        def errorResponse = result.getBody()
        errorResponse.exceptionName == "ItemNotFoundException"
        errorResponse.path == "path"
        errorResponse.httpStatus == HttpStatus.NOT_FOUND
        errorResponse.httpCode == HttpStatus.NOT_FOUND.value()
        errorResponse.message != null
        errorResponse.data == null
    }
    def "Handle : EstoresInternalException"() {
        given:
        def ex = easyRandom.nextObject(EstoresInternalException)
        when:
        def result = allExceptionsHandler.handleEstoresInternalException(ex, httpServletRequest)

        then:
        1 * httpServletRequest.getRequestURI() >> "path"
        result.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR
        def errorResponse = result.getBody()
        errorResponse.exceptionName == "EstoresInternalException"
        errorResponse.path == "path"
        errorResponse.httpStatus == HttpStatus.INTERNAL_SERVER_ERROR
        errorResponse.httpCode == HttpStatus.INTERNAL_SERVER_ERROR.value()
        errorResponse.message != null
        errorResponse.data == null
    }
    def "Handle : EstoresPropertyReferenceException"() {
        given:
        def ex = easyRandom.nextObject(EstoresPropertyReferenceException)
        when:
        def result = allExceptionsHandler.handleEstoresPropertyReferenceException(ex, httpServletRequest)

        then:
        1 * httpServletRequest.getRequestURI() >> "path"
        result.getStatusCode() == HttpStatus.BAD_REQUEST
        def errorResponse = result.getBody()
        errorResponse.exceptionName == "EstoresPropertyReferenceException"
        errorResponse.path == "path"
        errorResponse.httpStatus == HttpStatus.BAD_REQUEST
        errorResponse.httpCode == HttpStatus.BAD_REQUEST.value()
        errorResponse.message != null
        errorResponse.data == null
    }
}
