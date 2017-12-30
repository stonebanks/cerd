package com.themegalith.bks.yaccerd

import android.app.Application
import com.themegalith.bks.yaccerd.di.component.ApplicationComponent
import com.themegalith.bks.yaccerd.di.component.DaggerApplicationComponent
import com.themegalith.bks.yaccerd.di.module.ApplicationModule
import timber.log.Timber

/**
 * Created by allan on 27/12/17.
 */
class YaccerdApplication : Application() {

    companion object {
        lateinit var component : ApplicationComponent
    }

    fun createComponent() : ApplicationComponent =
        DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        component = createComponent()
        //component.inject(this)
    }

}