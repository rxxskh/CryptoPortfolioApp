package com.rxs.cryptoportfolioapp.data.remote

import com.rxs.cryptoportfolioapp.common.Constants
import com.rxs.cryptoportfolioapp.data.remote.dto.ranking_list.ListCoinDto
import com.rxs.cryptoportfolioapp.data.remote.dto.usdt_price.PricedUsdtDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinMarketCapApi {
    @GET("/v1/cryptocurrency/listings/latest")
    suspend fun getCoins(
        @Query("CMC_PRO_API_KEY") token: String = Constants.TOKEN,
        @Query("limit") limit: String = Constants.COINS_LIMIT
    ): ListCoinDto

    @GET("/v1/cryptocurrency/quotes/latest")
    suspend fun getUsdtPrice(
        @Query("CMC_PRO_API_KEY") token: String = Constants.TOKEN,
        @Query("symbol") symbol: String = "USDT",
        @Query("convert") convert: String = "RUB"
    ): PricedUsdtDto
}