package com.themegalith.bks.cerd.di.component

import com.themegalith.bks.cerd.presentation.ui.MainActivity
import com.themegalith.bks.cerd.di.module.MainModule
import com.themegalith.bks.cerd.di.scope.MainScope
import dagger.Subcomponent

/**
 * Created by allan on 27/12/17.
 */
@MainScope
@Subcomponent(modules= [(MainModule::class)])
interface MainSubComponent {
    fun inject(activity: MainActivity)
}