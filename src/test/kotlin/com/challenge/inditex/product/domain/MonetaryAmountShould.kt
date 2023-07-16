package com.challenge.inditex.product.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MonetaryAmountShould {

    @Test
    fun `correctly round up positive numbers`() {
        // Given
        val original = MonetaryAmountMother.random(amount = 123.456789)

        // When
        val rounded = original.round()

        // Then
        assertEquals(123.46, rounded.amount)
        assertEquals(original.currency, rounded.currency)
    }

    @Test
    fun `correctly round down positive numbers`() {
        // Given
        val original = MonetaryAmountMother.random(amount = 123.4512345)

        // When
        val rounded = original.round()

        // Then
        assertEquals(123.45, rounded.amount)
        assertEquals(original.currency, rounded.currency)
    }

    @Test
    fun `correctly round down negative numbers`() {
        // Given
        val original = MonetaryAmountMother.random(amount = -123.456789)

        // When
        val rounded = original.round()

        // Then
        assertEquals(-123.46, rounded.amount)
        assertEquals(original.currency, rounded.currency)
    }

    @Test
    fun `correctly round up negative numbers`() {
        // Given
        val original = MonetaryAmountMother.random(amount = -123.4512345)

        // When
        val rounded = original.round()

        // Then
        assertEquals(-123.45, rounded.amount)
        assertEquals(original.currency, rounded.currency)
    }

}