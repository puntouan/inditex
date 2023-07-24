package com.challenge.inditex.product.infrastructure.rest

import com.challenge.inditex.product.domain.MonetaryAmount
import com.challenge.inditex.product.domain.ProductRate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDateTime

class ProductRateDto (
    val brandId: Long,
    val productId: Long,
    val rateId: Long,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
    val price: String
)

@Component
class ProductRateDtoMapper(
    @Autowired val monetaryAmountMapper: MonetaryAmountDtoMapper
){

    fun mapFromDomainToDto(productRate: ProductRate): ProductRateDto {
        return ProductRateDto(
            brandId = productRate.brandId,
            productId = productRate.productId,
            rateId = productRate.rate.rateId,
            startDateTime = productRate.rate.startDateTime,
            endDateTime = productRate.rate.endDateTime,
            price = monetaryAmountMapper.mapFromDomainToStringDto(productRate.rate.price)
        )
    }

}

@Component
class MonetaryAmountDtoMapper{

    fun mapFromDomainToStringDto(monetaryAmount: MonetaryAmount): String{
        val rounded = monetaryAmount.round()
        return "${rounded.amount}${rounded.currency.symbol}"
    }

}