package com.themegalith.bks.cerd

import android.app.Application
import com.themegalith.bks.cerd.di.component.ApplicationComponent
import com.themegalith.bks.cerd.di.component.DaggerApplicationComponent
import com.themegalith.bks.cerd.di.module.ApplicationModule
import timber.log.Timber

/**
 * Created by allan on 27/12/17.
 */
class CerdApplication : Application() {

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
    }

}