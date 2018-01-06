package com.themegalith.bks.cerd.presentation.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.themegalith.bks.cerd.CerdApplication
import com.themegalith.bks.cerd.di.component.ApplicationComponent

/**
 * Created by allan on 27/12/17.
 */
abstract class BaseActivity: AppCompatActivity() {
     abstract fun injectDependencies(component : ApplicationComponent)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies(CerdApplication.component)
    }
}