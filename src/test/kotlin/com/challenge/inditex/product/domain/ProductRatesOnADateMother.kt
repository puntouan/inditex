package com.challenge.inditex.product.domain

import java.time.LocalDateTime
import kotlin.random.Random

class ProductRatesOnADateMother {

    companion object{

        private val rnd = Random(System.currentTimeMillis())

        fun random(
            brandId: Long = rnd.nextLong(0, Long.MAX_VALUE),
            productId: Long = rnd.nextLong(0, Long.MAX_VALUE),
            applicationDate: LocalDateTime = LocalDateTime.now(),
            rates: List<Rate> = RateMother.nRandom(5, applicationDate)
        ):ProductRatesOnADate{
            return ProductRatesOnADate(
                brandId = brandId,
                productId = productId,
                applicationDate = applicationDate,
                rates = rates
            )
        }

    }

}