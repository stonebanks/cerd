package com.themegalith.bks.yaccerd.di.component

import com.themegalith.bks.yaccerd.MainActivity
import com.themegalith.bks.yaccerd.di.module.MainModule
import com.themegalith.bks.yaccerd.di.scope.MainScope
import dagger.Subcomponent

/**
 * Created by allan on 27/12/17.
 */
@MainScope
@Subcomponent(modules= arrayOf(MainModule::class))
interface MainSubComponent {
    fun inject(activity: MainActivity)
}