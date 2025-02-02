package com.nickel.mackenscanner.network

import com.nickel.mackenscanner.BuildConfig
import com.nickel.mackenscanner.network.hanomacke.BasicAuthInterceptor
import com.nickel.mackenscanner.network.hanomacke.MackenRepository
import com.nickel.mackenscanner.network.hanomacke.MackenService
import com.nickel.mackenscanner.network.wasteside.WasteSideRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    internal fun provideMackenRepository(service: MackenService): MackenRepository =
        MackenRepository(service)

    @Provides
    @Singleton
    fun provideBasicAuthClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                BasicAuthInterceptor(
                    MackenService.MACKEN_USER_NAME,
                    BuildConfig.HANOMACKE_API_KEY
                )
            )
            .build()

    @Provides
    @Singleton
    internal fun provideMackenRetrofit(client: OkHttpClient): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(MackenService.MACKEN_URL)
            .client(client)
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