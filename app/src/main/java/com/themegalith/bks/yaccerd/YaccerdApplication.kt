package com.themegalith.bks.yaccerd

import android.app.Application
import com.themegalith.bks.yaccerd.di.component.ApplicationComponent
import com.themegalith.bks.yaccerd.di.component.DaggerApplicationComponent
import com.themegalith.bks.yaccerd.di.module.ApplicationModule

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
        component = createComponent()
        //component.inject(this)
    }

}