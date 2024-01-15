package com.rxs.cryptoportfolioapp.presentation.new_asset

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rxs.cryptoportfolioapp.common.Resource
import com.rxs.cryptoportfolioapp.common.toNewAssetBalance
import com.rxs.cryptoportfolioapp.common.toPricePerCoin
import com.rxs.cryptoportfolioapp.data.shared_prefs.Portfolio
import com.rxs.cryptoportfolioapp.data.shared_prefs.PortfolioCoin
import com.rxs.cryptoportfolioapp.domain.model.Coin
import com.rxs.cryptoportfolioapp.domain.usecase.AssetTransactionUseCase
import com.rxs.cryptoportfolioapp.domain.usecase.GetCoinsUseCase
import com.rxs.cryptoportfolioapp.domain.usecase.GetPortfolioUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewAssetViewModel @Inject constructor(
    private val getPortfolioUseCase: GetPortfolioUseCase,
    private val assetTransactionUseCase: AssetTransactionUseCase,
    private val getCoinsUseCase: GetCoinsUseCase
) : ViewModel() {

    private val _portfolio = MutableLiveData<Portfolio>()
    val portfolio: LiveData<Portfolio> = _portfolio
    private val _coinsData = MutableLiveData<Resource<List<Coin>>>()
    val coinsData: LiveData<Resource<List<Coin>>> = _coinsData
    private val _newAssetCoin = MutableLiveData<PortfolioCoin>()
    val newAssetCoin: LiveData<PortfolioCoin> = _newAssetCoin

    init {
        getPortfolio()
        getCoins()
    }

    fun selectCoin(coin: Coin) {
        viewModelScope.launch {
            _newAssetCoin.postValue(assetTransactionUseCase.getSelectedPortfolioCoin(coin = coin))
        }
    }

    fun applyTransaction(value: Double, investedPrice: Double? = null) {
        viewModelScope.launch {
            assetTransactionUseCase.applyTransaction(
                value = value,
                investedPrice = investedPrice,
                asset = _newAssetCoin.value!!
            )
        }
    }

    fun getFilteredCoinList(startText: String): List<Coin> {
        return _coinsData.value?.data?.filter {
            it.symbol.lowercase().startsWith(startText)
        } ?: emptyList()
    }

    fun getSelectedCoinPrice(): String {
        val selectedDataCoin = _coinsData.value?.data?.filter {
            it.name == _newAssetCoin.value?.coin?.name
        }?.getOrNull(0)

        return selectedDataCoin?.price?.toPricePerCoin() ?: ""
    }

    fun getCoinBalance(): String {
        return "${_newAssetCoin.value!!.value.toNewAssetBalance()} ${_newAssetCoin.value!!.coin!!.symbol}"
    }

    private fun getCoins() {
        viewModelScope.launch {
            getCoinsUseCase.getCoins().collect {
                _coinsData.postValue(it)
            }
        }
    }

    private fun getPortfolio() {
        viewModelScope.launch {
            val sharedPortfolio = getPortfolioUseCase.getPortfolio()
            _portfolio.postValue(sharedPortfolio)
        }
    }
}