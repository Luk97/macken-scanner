package com.nickel.mackenscanner.network

import com.nickel.mackenscanner.network.hanomacke.MackenRepository
import com.nickel.mackenscanner.network.hanomacke.MackenService
import com.nickel.mackenscanner.network.wasteside.WasteSideRepository
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
    internal fun provideMackenRetrofit(): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(MackenService.MACKEN_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    internal fun provideMackenService(retrofit: Retrofit): MackenService =
        retrofit.create(MackenService::class.java)

    @Provides
    @Singleton
    internal fun provideWasteSideRepository(): WasteSideRepository =
        WasteSideRepository()
}