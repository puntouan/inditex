package com.challenge.inditex.product.domain

import java.time.LocalDateTime
import kotlin.random.Random

class RateMother {

    companion object{

        private val rnd = Random(System.currentTimeMillis())

        private const val secondsInTenYears = 365 * 24 * 60 * 60L

        fun random(
            rateId: Long = rnd.nextLong(0, Long.MAX_VALUE),
            priority: Int = rnd.nextInt(0, 1000),
            startDateTime: LocalDateTime = LocalDateTime.now().minusSeconds(rnd.nextLong(0, secondsInTenYears)),
            endDateTime: LocalDateTime = LocalDateTime.now().plusSeconds(rnd.nextLong(0, secondsInTenYears)),
            price: MonetaryAmount = MonetaryAmountMother.random()
        ): Rate{
            return Rate(
                rateId = rateId,
                priority = priority,
                startDateTime = startDateTime,
                endDateTime = endDateTime,
                price = price
            )
        }

        fun nRandom(n: Int, applicationDate: LocalDateTime): List<Rate>{

            return (1..n).map {
                random(
                    startDateTime = applicationDate.minusSeconds(rnd.nextLong(0, secondsInTenYears)),
                    endDateTime = applicationDate.plusSeconds(rnd.nextLong(0, secondsInTenYears)),
                )
            }

        }

    }
}