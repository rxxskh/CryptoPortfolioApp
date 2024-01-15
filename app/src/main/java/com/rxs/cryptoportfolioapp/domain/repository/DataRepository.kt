package com.rxs.cryptoportfolioapp.domain.repository

import com.rxs.cryptoportfolioapp.data.shared_prefs.Portfolio

interface DataRepository {
    suspend fun save(data: Portfolio)
    suspend fun get(): Portfolio
}