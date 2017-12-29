package com.themegalith.bks.yaccerd.di.module

import com.themegalith.bks.yaccerd.MainActivity
import com.themegalith.bks.yaccerd.network.services.CoinMarketCapApi
import com.themegalith.bks.yaccerd.repository.Repository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by allan on 27/12/17.
 */
@Module
class MainModule(var activity: MainActivity) {
    init {
        this.activity = activity
    }
    @Provides @Singleton fun provideActivity() : MainActivity = this.activity

    @Provides @Singleton fun provideRepository(retrofit: Retrofit) : CoinMarketCapApi.Service =
            retrofit.create(CoinMarketCapApi.Service::class.java)
}