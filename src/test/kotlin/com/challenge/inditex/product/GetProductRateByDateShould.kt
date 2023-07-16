package com.challenge.inditex.product

import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.server.LocalServerPort

@SpringBootTest(webEnvironment = RANDOM_PORT)
class GetProductRateByDateShould {

    @LocalServerPort
    lateinit var port: String

    private val brandId = 1
    private val productId = 35455

    @Test
    fun `return the correct rate when the request is at 10-00h on the 14th`() {
        `should return the rate 1 when application date is`("2020-06-14T10:00:00")
    }

    @Test
    fun `return the correct rate when the request is at 16-00h on the 14th`() {

        val applicationDate = "2020-06-14T16:00:00"

        Given {
            param("date", applicationDate).
            port(port.toInt())
        } When {
            get("/brand/$brandId/product/$productId/prices")
        } Then  {
            statusCode(200)
            body("brandId", equalTo(1))
            body("productId", equalTo(35455))
            body("rateId", equalTo(2))
            body("startDateTime", equalTo("2020-06-14T15:00:00"))
            body("endDateTime", equalTo("2020-06-14T18:30:00"))
            body("price", equalTo("25.45€"))
        }

    }

    @Test
    fun `return the correct rate when the request is at 21-00h on the 14th`() {
        `should return the rate 1 when application date is`("2020-06-14T21:00:00")
    }

    @Test
    fun `return the correct rate when the request is at 10-00h on the 15th`() {

        val applicationDate = "2020-06-15T10:00:00"

        Given {
            param("date", applicationDate).
            port(port.toInt())
        } When {
            get("/brand/$brandId/product/$productId/prices")
        } Then  {
            statusCode(200)
            body("brandId", equalTo(1))
            body("productId", equalTo(35455))
            body("rateId", equalTo(3))
            body("startDateTime", equalTo("2020-06-15T00:00:00"))
            body("endDateTime", equalTo("2020-06-15T11:00:00"))
            body("price", equalTo("30.5€"))
        }

    }

    @Test
    fun `return the correct rate when the request is at 21-00h on the 16th`() {

        val applicationDate = "2020-06-16T21:00:00"

        Given {
            param("date", applicationDate).
            port(port.toInt())
        } When {
            get("/brand/$brandId/product/$productId/prices")
        } Then  {
            statusCode(200)
            body("brandId", equalTo(1))
            body("productId", equalTo(35455))
            body("rateId", equalTo(4))
            body("startDateTime", equalTo("2020-06-15T16:00:00"))
            body("endDateTime", equalTo("2020-12-31T23:59:59"))
            body("price", equalTo("38.95€"))
        }

    }

    @Test
    fun `return error there is no rate for product, brand and date`() {

        val applicationDate = "2020-06-10T10:00"

        Given {
            param("date", applicationDate).
            port(port.toInt())
        } When {
            get("/brand/$brandId/product/$productId/prices")
        } Then  {
            statusCode(404)
            body("status", equalTo("404 Not Found"))
            body(
                "error",
                equalTo("There is no rate for product $productId and brand $brandId for the date $applicationDate")
            )
        }

    }

    private fun `should return the rate 1 when application date is`(applicationDate: String) {
        Given {
            param("date", applicationDate).
            port(port.toInt())
        } When {
            get("/brand/$brandId/product/$productId/prices")
        } Then  {
            statusCode(200)
            body("brandId", equalTo(1))
            body("productId", equalTo(35455))
            body("rateId", equalTo(1))
            body("startDateTime", equalTo("2020-06-14T00:00:00"))
            body("endDateTime", equalTo("2020-12-31T23:59:59"))
            body("price", equalTo("35.5€"))
        }
    }

}