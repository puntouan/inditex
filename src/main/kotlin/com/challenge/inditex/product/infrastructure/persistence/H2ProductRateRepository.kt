package com.challenge.inditex.product.infrastructure.persistence

import com.challenge.inditex.product.domain.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.time.LocalDateTime

@Repository
class H2ProductRateRepository(
    @Autowired private val template: JdbcTemplate
):ProductRateRepository {

    override fun getProductRatesOnDate(productId: Long, brandId: Long, date: LocalDateTime): ProductRatesOnADate? {

        val rates = template.query(
            """
                SELECT * FROM PRICES 
                WHERE 
                    PRODUCT_ID = ? AND BRAND_ID = ? AND
                    START_DATE <= ? AND END_DATE >= ?
            """,
            this::mapRowToRate,
            productId,brandId, date, date
        )

        return if (rates.isEmpty()) null else
            ProductRatesOnADate(
                brandId = brandId,
                productId = productId,
                applicationDate = date,
                rates = rates
            )


    }

    // Parameter "index: Int", although it is not used, it is necessary for the correct choice
    // between the overloaded methods called 'query' in JdbcTemplate
    private fun mapRowToRate(rs: ResultSet, @Suppress("UNUSED_PARAMETER") index: Int): Rate{
        return Rate(
            rateId = rs.getLong("PRICE_LIST"),
            priority = rs.getInt("PRIORITY"),
            startDateTime = rs.getTimestamp("START_DATE").toLocalDateTime(),
            endDateTime = rs.getTimestamp("END_DATE").toLocalDateTime(),
            price = MonetaryAmount(
                amount = rs.getDouble("PRICE"),
                currency = Currency.valueOf(rs.getString("CURR"))
            )
        )
    }

}

