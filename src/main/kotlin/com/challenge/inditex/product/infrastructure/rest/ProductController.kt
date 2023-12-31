package com.challenge.inditex.product.infrastructure.rest

import com.challenge.inditex.product.domain.IGetProductRateByDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class ProductController(
    @Autowired private val getProductRateByDate: IGetProductRateByDate,
    @Autowired private val productRateDtoMapper: ProductRateDtoMapper
) {

    @GetMapping("/brand/{brandId}/product/{productId}/prices")
    fun getProductRateByDate(@PathVariable brandId: Long, @PathVariable productId: Long, @RequestParam date: LocalDateTime): ProductRateDto {

        return getProductRateByDate.execute(productId, brandId, date).let {
            productRateDtoMapper.mapFromDomainToDto(it)
        }

    }

}