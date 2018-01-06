package com.themegalith.bks.cerd.di.module

import com.themegalith.bks.cerd.CerdApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by allan on 27/12/17.
 */
@Module
class ApplicationModule(private val app: CerdApplication) {
    @Provides @Singleton fun provideApp() = app
}