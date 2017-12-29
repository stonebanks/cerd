package com.themegalith.bks.yaccerd.di.module

import com.themegalith.bks.yaccerd.YaccerdApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by allan on 27/12/17.
 */
@Module
class ApplicationModule(val app: YaccerdApplication) {
    @Provides @Singleton fun provideApp() = app
}