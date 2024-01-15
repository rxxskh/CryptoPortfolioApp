package com.rxs.cryptoportfolioapp.domain.usecase

import com.rxs.cryptoportfolioapp.domain.model.Coin
import com.rxs.cryptoportfolioapp.common.DispatcherProvider
import com.rxs.cryptoportfolioapp.common.Resource
import com.rxs.cryptoportfolioapp.data.remote.dto.ranking_list.toCoinList
import com.rxs.cryptoportfolioapp.data.remote.dto.usdt_price.toPricedCoin
import com.rxs.cryptoportfolioapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository,
    private val dispatcherProvider: DispatcherProvider
) {

    fun getCoins(): Flow<Resource<List<Coin>>> = flow {
        try {
            emit(Resource.Loading())
            val coins = repository.getCoins().toCoinList()
            emit(Resource.Success(data = coins))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "Exception"))
        }
    }.flowOn(dispatcherProvider.io)
}