package com.rxs.cryptoportfolioapp.data.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import com.rxs.cryptoportfolioapp.common.Constants
import com.rxs.cryptoportfolioapp.data.shared_prefs.Portfolio
import com.rxs.cryptoportfolioapp.domain.repository.DataRepository
import javax.inject.Inject

class SharedPreferencesRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : DataRepository {
    private val gson = Gson()

    override suspend fun save(data: Portfolio) {
        with(sharedPreferences.edit()) {
            val json: String = gson.toJson(data)
            putString(Constants.BALANCE_KEY, json)
            apply()
        }
    }

    override suspend fun get(): Portfolio {
        val json: String? = sharedPreferences.getString(Constants.BALANCE_KEY, null)
        return if (json != null) {
            gson.fromJson(json, Portfolio::class.java)
        } else {
            Portfolio()
        }
    }
}