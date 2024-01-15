package com.rxs.cryptoportfolioapp.domain.usecase

import com.rxs.cryptoportfolioapp.common.DispatcherProvider
import com.rxs.cryptoportfolioapp.data.shared_prefs.Portfolio
import com.rxs.cryptoportfolioapp.domain.repository.DataRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.roundToInt

class PortfolioBalanceChangeUseCase @Inject constructor(
    private val dataRepository: DataRepository,
    private val dispatcherProvider: DispatcherProvider
) {

    suspend fun investBalance(investedValue: Int, boughtUst: Double): Portfolio {
        return withContext(dispatcherProvider.io) {
            val sharedPortfolio = dataRepository.get()
            val oldBalance = sharedPortfolio.invRusBalance
            val oldAverageUsdt = sharedPortfolio.averageUsdt
            val oldUsdtBalance = if (oldAverageUsdt != 0.0) (oldBalance / oldAverageUsdt) else 0.0

            val newRusBalance = sharedPortfolio.invRusBalance.plus(investedValue)
            val newUsdtBalance = oldUsdtBalance.plus(boughtUst)
            val newAverageUsdt = ((newRusBalance / newUsdtBalance) * 100.0).roundToInt() / 100.0

            sharedPortfolio.apply {
                invRusBalance = newRusBalance
                invUsdtBalance = newUsdtBalance
                averageUsdt = newAverageUsdt
            }

            dataRepository.save(data = sharedPortfolio)
            sharedPortfolio
        }
    }

    suspend fun withdrawBalance(withdrawValue: Int): Portfolio {
        return withContext(dispatcherProvider.io) {
            val sharedPortfolio = dataRepository.get()

            if (withdrawValue <= sharedPortfolio.invRusBalance) {
                val newBalance = sharedPortfolio.invRusBalance.minus(withdrawValue)
                sharedPortfolio.apply {
                    invRusBalance = if (newBalance == 0) 0 else newBalance
                    invUsdtBalance = if (newBalance == 0) 0.0 else newBalance / averageUsdt
                    averageUsdt = if (newBalance == 0) 0.0 else averageUsdt
                }

                dataRepository.save(data = sharedPortfolio)
            }

            sharedPortfolio
        }
    }
}