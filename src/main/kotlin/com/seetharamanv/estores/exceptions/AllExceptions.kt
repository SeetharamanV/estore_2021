package com.seetharamanv.estores.exceptions

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.servlet.http.HttpServletRequest

class ItemBadRequestException(msg: String) : RuntimeException(msg)
class ItemNotFoundException(msg: String) : RuntimeException(msg)
class EstoresInternalException(msg: String) : RuntimeException(msg)
class EstoresPropertyReferenceException(msg: String) : RuntimeException(msg)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorResponse(
    var exceptionName: String,
    var message: String?,
    var httpCode: Int,
    var httpStatus: HttpStatus,
    val path: String,
    val data: Any? = null
)

interface HasData {
    fun getData(): Any?
}

@ControllerAdvice()
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class AllExceptionsHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(ItemBadRequestException::class)
    fun handleItemBadRequestException(ex: ItemBadRequestException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        return logAndReturn(ex, ex.message, request, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ItemNotFoundException::class)
    fun handleItemNotFoundException(ex: ItemNotFoundException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        return logAndReturn(ex, ex.message, request, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(EstoresInternalException::class)
    fun handleEstoresInternalException(ex: EstoresInternalException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        return logAndReturn(ex, ex.message, request, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(EstoresPropertyReferenceException::class)
    fun handleEstoresPropertyReferenceException(ex: EstoresPropertyReferenceException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        return logAndReturn(ex, ex.message, request, HttpStatus.BAD_REQUEST)
    }

    private fun logAndReturn(
            ex: Exception,
            message: String?,
            request: HttpServletRequest,
            httpStatus: HttpStatus
    ): ResponseEntity<ErrorResponse> {
        val exceptionName = ex.javaClass.simpleName
        val httpCode = httpStatus.value()

        val msg = if (message.isNullOrBlank()) {
            "No message provided."
        } else {
            message
        }

        var path = request.requestURI
        if (!request.queryString.isNullOrBlank()) {
            path += "?${request.queryString}"
        }

        var data: Any? = null
        if (ex is HasData)
            data = ex.getData()

        val error = ErrorResponse(exceptionName, msg, httpCode, httpStatus, path, data)

        if (httpCode < 500) {
            logger.info(error)
        } else {
            logger.error(error, ex)
        }

        return ResponseEntity(error, httpStatus)
    }
}