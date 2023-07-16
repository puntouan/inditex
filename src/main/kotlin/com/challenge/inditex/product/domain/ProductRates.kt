package com.challenge.inditex.product.domain

import java.time.LocalDateTime
import kotlin.math.round

data class ProductRatesOnADate(
    val brandId: Long,
    val productId: Long,
    val applicationDate: LocalDateTime,
    val rates: List<Rate>
) {

    private val rateWithMaxPriority = rates.maxByOrNull {
        it.priority
    }

    init {
        check(rates.all {it.isDateIncluded(applicationDate)}){
            "There is at least one rate that does not include the application date"
        }
        rateWithMaxPriority?.let {
            check(rates.filter { it.priority == rateWithMaxPriority.priority }.size == 1){
                "There is more than one rate to apply"
            }
        }
    }

    fun getRateToApply(): ProductRate? {
        return rateWithMaxPriority?.let {
            ProductRate(
                brandId,
                productId,
                it
            )
        }
    }

}

data class ProductRate(
    val brandId: Long,
    val productId: Long,
    val rate: Rate
)

data class Rate(
    val rateId: Long,
    val priority: Int,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
    val price: MonetaryAmount
){
    init {
        check(startDateTime.isBefore(endDateTime))
    }

    fun isDateIncluded(date: LocalDateTime): Boolean {
        return isAfterOrEqualStartDate(date)
            .and(isBeforeOrEqualEndDate(date))
    }

    private fun isAfterOrEqualStartDate(date: LocalDateTime): Boolean {
        return startDateTime.isBefore(date)
            .or(startDateTime.isEqual(date))
    }

    private fun isBeforeOrEqualEndDate(date: LocalDateTime): Boolean {
        return endDateTime.isAfter(date)
            .or(endDateTime.isEqual(date))
    }
}

data class MonetaryAmount(
    val amount: Double,
    val currency: Currency
){

    private fun roundAmount(decimals: Int = 2): Double{
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(amount * multiplier) / multiplier
    }

    fun round(decimals: Int = 2): MonetaryAmount{
        return MonetaryAmount(
            amount = roundAmount(decimals),
            currency = currency
        )
    }

}

enum class Currency(val symbol: String) {
    EUR("€")
}