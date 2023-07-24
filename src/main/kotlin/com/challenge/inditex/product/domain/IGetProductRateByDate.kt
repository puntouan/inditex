package com.challenge.inditex.product.domain

import java.time.LocalDateTime

interface IGetProductRateByDate {

    fun execute(productId: Long, brandId: Long, date: LocalDateTime): ProductRate

}