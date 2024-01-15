package com.rxs.cryptoportfolioapp.domain.usecase

import android.util.Log
import com.rxs.cryptoportfolioapp.common.DispatcherProvider
import com.rxs.cryptoportfolioapp.data.shared_prefs.Portfolio
import com.rxs.cryptoportfolioapp.data.shared_prefs.PortfolioCoin
import com.rxs.cryptoportfolioapp.domain.model.Coin
import com.rxs.cryptoportfolioapp.domain.repository.DataRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AssetTransactionUseCase @Inject constructor(
    private val dataRepository: DataRepository,
    private val dispatcherProvider: DispatcherProvider
) {

    suspend fun getSelectedPortfolioCoin(coin: Coin): PortfolioCoin {
        return withContext(dispatcherProvider.io) {
            val sharedPortfolio = dataRepository.get()
            val foundCoin = sharedPortfolio.assets.firstOrNull {
                it.coin?.symbol == coin.symbol
            } ?: PortfolioCoin(coin = coin)
            foundCoin
        }
    }

    suspend fun applyTransaction(
        value: Double,
        investedPrice: Double?,
        asset: PortfolioCoin
    ): Portfolio {
        return withContext(dispatcherProvider.io) {
            val sharedPortfolio = dataRepository.get()
            sharedPortfolio.assets.firstOrNull {
                it.coin?.symbol == asset.coin?.symbol
            }.let { sharedPortfolio.assets.remove(it) }

            if (investedPrice == null) {
                if (asset.value >= value) {
                    asset.value = asset.value.minus(value)
                }
                if (asset.value != 0.0) {
                    sharedPortfolio.assets.add(asset)
                }
            } else {
                asset.value = asset.value.plus(value)
                asset.investedPrice = (asset.investedPrice * asset.value).plus(investedPrice * value) / asset.value
                sharedPortfolio.assets.add(asset)
            }

            dataRepository.save(data = sharedPortfolio)
            sharedPortfolio
        }
    }
}