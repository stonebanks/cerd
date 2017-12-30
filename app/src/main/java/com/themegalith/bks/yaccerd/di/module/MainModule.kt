package com.themegalith.bks.yaccerd.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.themegalith.bks.yaccerd.MainActivity
import com.themegalith.bks.yaccerd.di.scope.MainScope
import com.themegalith.bks.yaccerd.domain.interactor.TickerGetterInteractor
import com.themegalith.bks.yaccerd.network.CoinMarketCapApi
import com.themegalith.bks.yaccerd.repository.Repository
import com.themegalith.bks.yaccerd.viewModel.MainActivityViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by allan on 27/12/17.
 */

@Module
class MainModule(val activity: MainActivity) {
    @Provides @Singleton fun provideActivity() : MainActivity = this.activity

    @Provides @MainScope fun provideRepository(retrofit: Retrofit) : CoinMarketCapApi.Service =
            retrofit.create(CoinMarketCapApi.Service::class.java)

    @Provides @MainScope fun provideInteractor(repository: Repository): TickerGetterInteractor = TickerGetterInteractor(repository)

    @Provides @MainScope fun provideMainActivityViewModel(interactor: TickerGetterInteractor) : MainActivityViewModel {
        var obj = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)){
                    return MainActivityViewModel(interactor) as T
                }
                throw IllegalArgumentException("")
            }
        }
        return obj.create(MainActivityViewModel::class.java)
    }
}