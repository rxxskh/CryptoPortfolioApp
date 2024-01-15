package com.rxs.cryptoportfolioapp.common

import java.text.NumberFormat
import java.util.Locale

private fun convert(): String = ""


// Portfolio -> 15 000 ₽
fun Int.toRussianFormat(): String {
    val numberFormat = NumberFormat.getNumberInstance(Locale("ru", "RU"))
    numberFormat.maximumFractionDigits = 0
    return numberFormat.format(this)
}

// Portfolio -> 99 108,86 ₽
fun Double.toRussianFormat(): String {
    val numberFormat = NumberFormat.getNumberInstance(Locale("ru", "RU"))
    numberFormat.maximumFractionDigits = 2
    return numberFormat.format(this)
}

fun Int.toRussianCurrency(): String {
    return this.toRussianFormat() + " ₽"
}

fun Double.toRussianCurrency(): String {
    return this.toRussianFormat() + " ₽"
}

fun Int.toUsdtCurrency(): String {
    return this.toRussianFormat() + " USDT"
}

fun Double.toUsdtCurrency(): String {
    return this.toRussianFormat() + " USDT"
}

fun Int.toUsCurrency(): String {
    return this.toRussianFormat() + " $"
}

fun Double.toUsCurrency(): String {
    return this.toRussianFormat() + " $"
}

// Ranking List -> Цена монеты в курсах
fun Double.toRankingListPrice(): String {
    val numberFormat = NumberFormat.getNumberInstance(Locale("ru", "RU"))
    val multipliers = listOf(1, 100, 10000, 1000000, 100000000)

    for (multiplier in multipliers) {
        val newValue = this * multiplier
        if (newValue.toInt() > 0) {
            numberFormat.maximumFractionDigits = multiplier.toString().length + 1
            return "${numberFormat.format(this)} $"
        }
    }

    return ""
}

// New Asset Buy Value -> Баланс монеты -> 1 000,0001 BTC
fun Double.toNewAssetBalance(): String {
    val numberFormat = NumberFormat.getNumberInstance(Locale("ru", "RU"))
    numberFormat.maximumFractionDigits =
        this.toString().substring(this.toString().indexOf(".") + 1).length
    return numberFormat.format(this)
}

// New Asset Buy Value -> Цена за монету
fun Double.toPricePerCoin(): String {
    val multipliers = listOf(1, 100, 10000, 1000000, 100000000)

    for (multiplier in multipliers) {
        val newValue = this * multiplier
        if (newValue.toInt() > 0) {
            return String.format("%.${multiplier.toString().length + 1}f", this)
        }
    }

    return ""
}