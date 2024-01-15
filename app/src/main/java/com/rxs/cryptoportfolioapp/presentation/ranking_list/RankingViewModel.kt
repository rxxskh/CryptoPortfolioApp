package com.rxs.cryptoportfolioapp.presentation.ranking_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rxs.cryptoportfolioapp.domain.model.Coin
import com.rxs.cryptoportfolioapp.common.Resource
import com.rxs.cryptoportfolioapp.domain.usecase.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase
) : ViewModel() {

    private val _coinsData = MutableLiveData<Resource<List<Coin>>>()
    val coinsData: LiveData<Resource<List<Coin>>> = _coinsData

    init {
        getCoins()
    }

    fun getFilteredCoinList(startText: String): List<Coin> {
        return _coinsData.value?.data?.filter {
            it.symbol.lowercase().startsWith(startText)
        } ?: emptyList()
    }

    private fun getCoins() {
        viewModelScope.launch {
            getCoinsUseCase.getCoins().collect{
                _coinsData.postValue(it)
            }
        }
    }
}