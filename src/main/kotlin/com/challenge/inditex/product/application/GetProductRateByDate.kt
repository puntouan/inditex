package com.challenge.inditex.product.application

import com.challenge.inditex.product.domain.IGetProductRateByDate
import com.challenge.inditex.product.domain.ProductRate
import com.challenge.inditex.product.domain.ProductRateRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class GetProductRateByDate(
    @Autowired private val productRateRepository: ProductRateRepository
) : IGetProductRateByDate {

    override fun execute(productId: Long, brandId: Long, date: LocalDateTime): ProductRate {

        return productRateRepository
            .getProductRatesOnDate(productId, brandId, date)
            ?.getRateToApply()
            ?: throw MissingProductRateOnDateException(productId, brandId, date)

    }

}

class MissingProductRateOnDateException(productId: Long, brandId: Long, date: LocalDateTime):
    RuntimeException("There is no rate for product $productId and brand $brandId for the date $date")