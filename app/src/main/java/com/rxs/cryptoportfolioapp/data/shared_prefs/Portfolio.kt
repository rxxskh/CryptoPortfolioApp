package com.rxs.cryptoportfolioapp.data.shared_prefs

data class Portfolio(
    var invRusBalance: Int = 0,
    var invUsdtBalance: Double = 0.0,
    var averageUsdt: Double = 0.0,

    var assets: MutableList<PortfolioCoin> = mutableListOf(),

    var usdtPrice: Double = 0.0,
    var curUsdtBalance: Double = 0.0,
    var curRusBalance: Int = 0,

    var rusProfitLoss: Int = 0,
    var profitLossPercent: String = "-"
)