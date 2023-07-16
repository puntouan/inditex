package com.challenge.inditex.product.application

import com.challenge.inditex.product.domain.ProductRateRepository
import com.challenge.inditex.product.domain.ProductRatesOnADate
import com.challenge.inditex.product.domain.RateMother
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

class GetProductRateByDateShould{

    private val productRateRepository: ProductRateRepository = mockk()
    private val getProductRateByDate = GetProductRateByDate(productRateRepository)

    private val brandId = 1L
    private val productId = 1L
    private val applicationDate = LocalDateTime.now()

    @Test
    fun `return the highest priority rate if it exists`(){

        // Given
        val rateToApply = RateMother.random(
            startDateTime = applicationDate.minusDays(1),
            endDateTime = applicationDate.plusDays(1),
            priority = Int.MAX_VALUE
        )

        val productRatesOnADate = ProductRatesOnADate(
            brandId = brandId,
            productId = productId,
            applicationDate = applicationDate,
            rates = RateMother.nRandom(3, applicationDate = applicationDate) + rateToApply
        )

        every { productRateRepository.getProductRatesOnDate(productId, brandId, applicationDate) }
            .returns(productRatesOnADate)

        // When
        val returnedProductRate = getProductRateByDate.execute(productId, brandId, applicationDate)

        // Then
        assertEquals(rateToApply, returnedProductRate.rate)
        assertEquals(brandId, returnedProductRate.brandId)
        assertEquals(productId, returnedProductRate.productId)

    }

    @Test
    fun `fail if there is no priority rate`(){

        // Given
        val productRatesOnADate = ProductRatesOnADate(
            brandId = brandId,
            productId = productId,
            applicationDate = applicationDate,
            rates = emptyList()
        )

        every { productRateRepository.getProductRatesOnDate(productId, brandId, applicationDate) }
            .returns(productRatesOnADate)

        // Then
        val exc = assertThrows<MissingProductRateOnDateException> {
            // When
            getProductRateByDate.execute(productId, brandId, applicationDate)
        }
        assertTrue(exc.message!!.contains("There is no rate for product"))

    }

}