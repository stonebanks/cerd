package com.themegalith.bks.yaccerd.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.themegalith.bks.yaccerd.YaccerdApplication
import com.themegalith.bks.yaccerd.di.component.ApplicationComponent

/**
 * Created by allan on 27/12/17.
 */
abstract class BaseActivity: AppCompatActivity() {
     abstract fun injectDependencies(component : ApplicationComponent)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies(YaccerdApplication.component)
    }
}