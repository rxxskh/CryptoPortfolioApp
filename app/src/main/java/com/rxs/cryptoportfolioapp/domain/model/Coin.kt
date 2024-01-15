package com.rxs.cryptoportfolioapp.domain.model

data class Coin(
    val name: String,
    val symbol: String,
    val price: Double,
    val percentChange24: Double
)