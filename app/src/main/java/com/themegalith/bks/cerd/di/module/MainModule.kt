package com.themegalith.bks.cerd.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.themegalith.bks.cerd.presentation.ui.MainActivity
import com.themegalith.bks.cerd.di.scope.MainScope
import com.themegalith.bks.cerd.domain.interactor.TickerGetterInteractor
import com.themegalith.bks.cerd.network.CoinbinApi
import com.themegalith.bks.cerd.repository.CoinbinRepository
import com.themegalith.bks.cerd.viewModel.MainActivityViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by allan on 27/12/17.
 */

@Module
class MainModule(private val activity: MainActivity) {
    @Provides @Singleton fun provideActivity() : MainActivity = this.activity

    @Provides @MainScope fun provideCoinbinRepository(retrofit: Retrofit) : CoinbinRepository =
            CoinbinRepository(retrofit.create(CoinbinApi.Service::class.java))

    @Provides @MainScope fun provideInteractor(repository: CoinbinRepository): TickerGetterInteractor = TickerGetterInteractor(repository)

    @Provides @MainScope fun provideMainActivityViewModel(interactor: TickerGetterInteractor) : MainActivityViewModel {
        val obj = object : ViewModelProvider.Factory {
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