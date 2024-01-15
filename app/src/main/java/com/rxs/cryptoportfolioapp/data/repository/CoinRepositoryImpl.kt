package com.rxs.cryptoportfolioapp.data.repository

import com.rxs.cryptoportfolioapp.data.remote.dto.ranking_list.ListCoinDto
import com.rxs.cryptoportfolioapp.domain.repository.CoinRepository
import com.rxs.cryptoportfolioapp.data.remote.CoinMarketCapApi
import com.rxs.cryptoportfolioapp.data.remote.dto.usdt_price.PricedUsdtDto
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinMarketCapApi
) : CoinRepository {

    override suspend fun getCoins(): ListCoinDto {
        return api.getCoins()
    }

    override suspend fun getUsdtPrice(): PricedUsdtDto {
        return api.getUsdtPrice()
    }
}