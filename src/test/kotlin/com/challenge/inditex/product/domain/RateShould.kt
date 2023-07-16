package com.challenge.inditex.product.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class RateShould {

    @Test
    fun `not allow a start date after the end date`() {

        // Given
        val startDate = LocalDateTime.now()
        val endDate = startDate.minusDays(1)

        // Then
        assertThrows(IllegalStateException::class.java){
            // When
            RateMother.random(
                startDateTime = startDate,
                endDateTime = endDate
            )
        }

    }

    @Test
    fun `return that the date is not included when it is before the start date`() {
        // Given
        val rate = RateMother.random(
            startDateTime = LocalDateTime.now(),
            endDateTime = LocalDateTime.now().plusDays(5)
        )

        // When
        val isDateIncluded = rate.isDateIncluded( rate.startDateTime.minusDays(1))

        // Then
        assertFalse(isDateIncluded)
    }

    @Test
    fun `return that the date is included when it is equal to the start date`() {
        // Given
        val rate = RateMother.random(
            startDateTime = LocalDateTime.now(),
            endDateTime = LocalDateTime.now().plusDays(5)
        )

        // When
        val isDateIncluded = rate.isDateIncluded( rate.startDateTime)

        // Then
        assertTrue(isDateIncluded)
    }

    @Test
    fun `return that the date is included when it is after the start date and before the end date`() {
        // Given
        val rate = RateMother.random(
            startDateTime = LocalDateTime.now(),
            endDateTime = LocalDateTime.now().plusDays(5)
        )

        // When
        val isDateIncluded = rate.isDateIncluded( rate.startDateTime.plusDays(1))

        // Then
        assertTrue(isDateIncluded)
    }

    @Test
    fun `return that the date is included when it is equal to the end date`() {
        // Given
        val rate = RateMother.random(
            startDateTime = LocalDateTime.now(),
            endDateTime = LocalDateTime.now().plusDays(5)
        )

        // When
        val isDateIncluded = rate.isDateIncluded( rate.endDateTime)

        // Then
        assertTrue(isDateIncluded)
    }

    @Test
    fun `return that the date is not included when it is after the end date`() {
        // Given
        val rate = RateMother.random(
            startDateTime = LocalDateTime.now(),
            endDateTime = LocalDateTime.now().plusDays(5)
        )

        // When
        val isDateIncluded = rate.isDateIncluded( rate.endDateTime.plusDays(1))

        // Then
        assertFalse(isDateIncluded)
    }

}