package com.rxs.cryptoportfolioapp.domain.usecase

import android.util.Log
import com.rxs.cryptoportfolioapp.common.DispatcherProvider
import com.rxs.cryptoportfolioapp.common.toRussianFormat
import com.rxs.cryptoportfolioapp.data.remote.dto.ranking_list.toCoinList
import com.rxs.cryptoportfolioapp.data.remote.dto.usdt_price.toPricedCoin
import com.rxs.cryptoportfolioapp.data.shared_prefs.Portfolio
import com.rxs.cryptoportfolioapp.domain.repository.CoinRepository
import com.rxs.cryptoportfolioapp.domain.repository.DataRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PortfolioCurrentInfoUseCase @Inject constructor(
    private val dataRepository: DataRepository,
    private val coinRepository: CoinRepository,
    private val dispatcherProvider: DispatcherProvider
) {

    suspend fun getCurrentPortfolio(): Portfolio {
        return withContext(dispatcherProvider.io) {
            val sharedPortfolio = getPortfolioWithCurrentPrices()

            val usdtPrice = coinRepository.getUsdtPrice().toPricedCoin().price
            val curUsdtBalance = sharedPortfolio.assets.sumOf {
                it.value * it.currentPrice
            }
            val curRusBalance = (curUsdtBalance * usdtPrice).toInt()

            val rusProfitLoss = curRusBalance - sharedPortfolio.invRusBalance
            val profitLossPercent = if (sharedPortfolio.invRusBalance > 0) {
                ((rusProfitLoss.toDouble() / sharedPortfolio.invRusBalance.toDouble()) * 100.0).toRussianFormat() + "%"
            } else {
                "-"
            }

            sharedPortfolio.apply {
                this.usdtPrice = usdtPrice
                this.curUsdtBalance = curUsdtBalance
                this.curRusBalance = curRusBalance
                this.rusProfitLoss = rusProfitLoss
                this.profitLossPercent = profitLossPercent
            }

            dataRepository.save(data = sharedPortfolio)
            sharedPortfolio
        }
    }

    private suspend fun getPortfolioWithCurrentPrices(): Portfolio {
        return withContext(dispatcherProvider.io) {
            val sharedPortfolio = dataRepository.get()
            val coinsData = coinRepository.getCoins().toCoinList()

            sharedPortfolio.assets.map { portfolioCoin ->
                portfolioCoin.coin?.symbol.let {
                    val currentPrice = coinsData.firstOrNull { coin ->
                        coin.symbol == (portfolioCoin.coin?.symbol)
                    }?.price ?: 0.0
                    val profitLoss =
                        (currentPrice - portfolioCoin.investedPrice) * portfolioCoin.value
                    portfolioCoin.apply {
                        this.currentPrice = currentPrice
                        this.profitLoss = profitLoss
                    }
                }

            }

            dataRepository.save(data = sharedPortfolio)
            sharedPortfolio
        }
    }
}