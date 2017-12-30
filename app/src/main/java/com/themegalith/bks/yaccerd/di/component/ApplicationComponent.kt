package com.themegalith.bks.yaccerd.di.component

import com.themegalith.bks.yaccerd.di.module.ApplicationModule
import com.themegalith.bks.yaccerd.di.module.MainModule
import com.themegalith.bks.yaccerd.di.module.NetworkModule
import com.themegalith.bks.yaccerd.presentation.BaseActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by allan on 27/12/17.
 */
@Singleton
@Component(modules = arrayOf(ApplicationModule::class, NetworkModule::class))
interface ApplicationComponent {
    fun inject(baseActivity: BaseActivity)

    fun plus(mainModule: MainModule) : MainSubComponent
}