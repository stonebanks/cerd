package com.themegalith.bks.yaccerd.di.module

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


/**
 * Created by allan on 27/12/17.
 */
@Module
class NetworkModule {

    @Provides @Singleton fun provideMoshi(): Moshi =
            Moshi.Builder().add(KotlinJsonAdapterFactory())
                    .build()

    @Provides @Singleton fun provideRetrofit(moshi: Moshi): Retrofit =
            Retrofit.Builder()
                    .baseUrl("https://api.coinmarketcap.com/v1/")
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
}