package com.challenge.inditex.product.domain

import kotlin.random.Random

class MonetaryAmountMother {

    companion object{

        private val rnd = Random(System.currentTimeMillis())

        fun random(
            amount: Double = rnd.nextDouble(0.0, 10000.0),
            currency: Currency = Currency.values().random(rnd)
        ): MonetaryAmount{
            return MonetaryAmount(
                amount = amount,
                currency = currency
            )
        }
    }

}