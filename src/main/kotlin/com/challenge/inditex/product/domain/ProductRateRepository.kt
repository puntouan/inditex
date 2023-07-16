package com.challenge.inditex.product.domain

import java.time.LocalDateTime

interface ProductRateRepository {

    fun getProductRatesOnDate(productId: Long, brandId: Long, date: LocalDateTime): ProductRatesOnADate?

}