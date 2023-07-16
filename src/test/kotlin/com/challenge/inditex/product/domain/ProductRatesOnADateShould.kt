package com.challenge.inditex.product.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ProductRatesOnADateShould {

    @Test
    fun `not allow a rate that does not include the application date`() {

        val applicationDate = LocalDateTime.now()

        val rateIncludingApplicationDate = RateMother.random(
            startDateTime = applicationDate.minusDays(1),
            endDateTime = applicationDate.plusDays(1)
        )
        val rateNotIncludingApplicationDate = RateMother.random(
            startDateTime = applicationDate.plusDays(1),
            endDateTime = applicationDate.plusDays(2)
        )

        // Then
        var exc = Assertions.assertThrows(IllegalStateException::class.java) {
            // When
            ProductRatesOnADateMother.random(
                rates = listOf(rateIncludingApplicationDate, rateNotIncludingApplicationDate)
            )
        }
        assert(exc.message!!.contains("at least one rate that does not include the application date"))

        // Then
        exc = Assertions.assertThrows(IllegalStateException::class.java) {
            // When
            ProductRatesOnADateMother.random(
                rates = listOf(rateNotIncludingApplicationDate, rateIncludingApplicationDate)
            )
        }
        assert(exc.message!!.contains("at least one rate that does not include the application date"))

    }

    @Test
    fun `not allow more than rate to apply`() {

        // Given
        val applicationDate = LocalDateTime.now()
        val rateStartDate = applicationDate.minusDays(1)
        val rateEndDate = applicationDate.plusDays(1)

        val rateOne = RateMother.random(
            startDateTime = rateStartDate,
            endDateTime = rateEndDate,
            priority = 1
        )
        val rateTwo = RateMother.random(
            startDateTime = rateStartDate,
            endDateTime = rateEndDate,
            priority = 2
        )
        val rateThree = RateMother.random(
            startDateTime = rateStartDate,
            endDateTime = rateEndDate,
            priority = 2
        )

        // Then
        val exc = Assertions.assertThrows(IllegalStateException::class.java) {
            // When
            ProductRatesOnADateMother.random(
                rates = listOf(rateOne, rateTwo, rateThree)
            )
        }
        assert(exc.message!!.contains("There is more than one rate to apply"))

    }

    @Test
    fun `return as a rate to be applied to the one with the highest priority`() {

        // Given
        val applicationDate = LocalDateTime.now()
        val rateStartDate = applicationDate.minusDays(1)
        val rateEndDate = applicationDate.plusDays(1)

        val rateOne = RateMother.random(
            startDateTime = rateStartDate,
            endDateTime = rateEndDate,
            priority = 1
        )

        val rateTwo = RateMother.random(
            startDateTime = rateStartDate,
            endDateTime = rateEndDate,
            priority = 2
        )

        val rateWithMaxPriority = RateMother.random(
            startDateTime = rateStartDate,
            endDateTime = rateEndDate,
            priority = 5
        )

        val productRatesOnADate = ProductRatesOnADateMother.random(
            rates = listOf(rateOne, rateWithMaxPriority, rateTwo)
        )

        // When
        val rateToApply = productRatesOnADate.getRateToApply()

        // Then
        assertEquals(productRatesOnADate.productId, rateToApply!!.productId)
        assertEquals(productRatesOnADate.brandId, rateToApply.brandId)
        assertEquals(rateWithMaxPriority, rateToApply.rate)

    }

}