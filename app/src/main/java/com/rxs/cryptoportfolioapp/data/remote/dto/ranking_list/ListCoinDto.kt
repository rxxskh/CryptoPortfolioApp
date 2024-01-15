package com.rxs.cryptoportfolioapp.data.remote.dto.ranking_list

import com.rxs.cryptoportfolioapp.domain.model.Coin

data class ListCoinDto(
    val data: List<Data>,
    val status: Status
)

fun ListCoinDto.toCoinList(): List<Coin> {
    val coinList = mutableListOf<Coin>()
    data.map {
        coinList.add(
            Coin(
                name = it.name,
                symbol = it.symbol,
                price = it.quote.USD.price,
                percentChange24 = (it.quote.USD.percent_change_24h * 100.0).toInt() / 100.0
            )
        )
    }
    return coinList
}