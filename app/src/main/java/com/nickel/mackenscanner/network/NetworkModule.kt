package com.nickel.mackenscanner.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    internal fun provideMackenRepository(service: MackenService): MackenRepository =
        MackenRepository(service)

    @Provides
    @Singleton
    internal fun provideRetrofit(): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(MackenService.MACKEN_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    internal fun provideMackenService(retrofit: Retrofit): MackenService =
        retrofit.create(MackenService::class.java)
}