package com.rxs.cryptoportfolioapp.data.remote.dto.usdt_price

data class Platform(
    val id: Int,
    val name: String,
    val slug: String,
    val symbol: String,
    val token_address: String
)