package com.rxs.cryptoportfolioapp.data.remote.dto.usdt_price

import com.rxs.cryptoportfolioapp.domain.model.PricedUsdt

data class PricedUsdtDto(
    val data: Data,
    val status: Status
)

fun PricedUsdtDto.toPricedCoin(): PricedUsdt {
    return PricedUsdt(
        price = data.USDT.quote.RUB.price
    )
}