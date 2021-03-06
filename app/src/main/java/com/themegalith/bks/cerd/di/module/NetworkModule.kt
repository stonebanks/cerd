package com.themegalith.bks.cerd.di.module

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Created by allan on 27/12/17.
 */
@Module
class NetworkModule {

    @Provides @Singleton fun provideMoshi(): Moshi =
            Moshi.Builder().add(KotlinJsonAdapterFactory())
                    .build()

    @Provides @Singleton fun provideHttpClient(): OkHttpClient{
        return OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build()
    }

    @Provides @Singleton fun provideRetrofit(moshi: Moshi, httpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                    .baseUrl("https://api.ff.com/v1/")
                    .client(httpClient)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
}