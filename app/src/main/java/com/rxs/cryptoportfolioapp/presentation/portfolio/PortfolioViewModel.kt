package com.rxs.cryptoportfolioapp.presentation.portfolio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rxs.cryptoportfolioapp.data.shared_prefs.Portfolio
import com.rxs.cryptoportfolioapp.domain.usecase.PortfolioBalanceChangeUseCase
import com.rxs.cryptoportfolioapp.domain.usecase.PortfolioCurrentInfoUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PortfolioViewModel @Inject constructor(
    private val portfolioCurrentInfoUseCase: PortfolioCurrentInfoUseCase,
    private val portfolioBalanceChangeUseCase: PortfolioBalanceChangeUseCase
) : ViewModel() {

    private val _portfolio = MutableLiveData<Portfolio>()
    val portfolio: LiveData<Portfolio> = _portfolio

    init {
        getCurrentPortfolio()
    }

    fun investBalance(investedValue: Int, boughtUst: Double) {
        viewModelScope.launch {
            _portfolio.postValue(
                portfolioBalanceChangeUseCase.investBalance(
                    investedValue = investedValue,
                    boughtUst = boughtUst
                )
            )
        }
    }

    fun withdrawBalance(withdrawValue: Int) {
        viewModelScope.launch {
            _portfolio.postValue(
                portfolioBalanceChangeUseCase.withdrawBalance(
                    withdrawValue = withdrawValue
                )
            )
        }
    }

    fun getCurrentPortfolio() {
        viewModelScope.launch {
            val sharedPortfolioWithPrices = portfolioCurrentInfoUseCase.getCurrentPortfolio()
            _portfolio.postValue(sharedPortfolioWithPrices)
        }
    }
}