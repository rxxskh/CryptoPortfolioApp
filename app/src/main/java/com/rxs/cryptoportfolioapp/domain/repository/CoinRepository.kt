package com.rxs.cryptoportfolioapp.domain.repository

import com.rxs.cryptoportfolioapp.data.remote.dto.ranking_list.ListCoinDto
import com.rxs.cryptoportfolioapp.data.remote.dto.usdt_price.PricedUsdtDto

interface CoinRepository {
    suspend fun getCoins(): ListCoinDto
    suspend fun getUsdtPrice(): PricedUsdtDto
}