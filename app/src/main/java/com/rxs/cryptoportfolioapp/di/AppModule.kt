package com.rxs.cryptoportfolioapp.di

import android.content.Context
import android.content.SharedPreferences
import com.rxs.cryptoportfolioapp.common.Constants
import com.rxs.cryptoportfolioapp.data.remote.CoinMarketCapApi
import com.rxs.cryptoportfolioapp.data.repository.CoinRepositoryImpl
import com.rxs.cryptoportfolioapp.data.repository.SharedPreferencesRepository
import com.rxs.cryptoportfolioapp.domain.repository.CoinRepository
import com.rxs.cryptoportfolioapp.domain.repository.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCoinMarketCapApi(): CoinMarketCapApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinMarketCapApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(api: CoinMarketCapApi): CoinRepository {
        return CoinRepositoryImpl(api = api)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.SHARED_KEY, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideDataRepository(sharedPreferences: SharedPreferences): DataRepository {
        return SharedPreferencesRepository(sharedPreferences = sharedPreferences)
    }
}