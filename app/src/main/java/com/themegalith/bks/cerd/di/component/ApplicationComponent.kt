package com.themegalith.bks.cerd.di.component

import com.themegalith.bks.cerd.di.module.ApplicationModule
import com.themegalith.bks.cerd.di.module.MainModule
import com.themegalith.bks.cerd.di.module.NetworkModule
import com.themegalith.bks.cerd.presentation.ui.BaseActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by allan on 27/12/17.
 */
@Singleton
@Component(modules = [(ApplicationModule::class), (NetworkModule::class)])
interface ApplicationComponent {
    fun inject(baseActivity: BaseActivity)

    fun plus(mainModule: MainModule) : MainSubComponent
}