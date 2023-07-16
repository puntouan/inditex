package com.challenge.inditex.product.infrastructure

import com.challenge.inditex.product.application.MissingProductRateOnDateException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody


@ControllerAdvice
class ExceptionsHandler {

    @ResponseBody
    @ExceptionHandler(MissingProductRateOnDateException::class)
    protected fun handlePriceNotFound(ex: MissingProductRateOnDateException): ResponseEntity<Map<String,String>> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(mapOf(
                "status" to "${HttpStatus.NOT_FOUND.value()} ${HttpStatus.NOT_FOUND.reasonPhrase}",
                "error" to ex.message!!
            ))
    }

}