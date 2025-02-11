package com.nickel.mackenscanner.network

import com.nickel.mackenscanner.BuildConfig
import com.nickel.mackenscanner.network.api.MackenClient
import com.nickel.mackenscanner.network.auth.BasicAuthInterceptor
import com.nickel.mackenscanner.network.hanomacke.MackenRepository
import com.nickel.mackenscanner.network.api.MackenService
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
    fun provideMackenRepository(client: MackenClient): MackenRepository =
        MackenRepository(client)

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
    fun provideMackenRetrofit(client: OkHttpClient): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(MackenService.MACKEN_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideMackenService(retrofit: Retrofit): MackenService =
        retrofit.create(MackenService::class.java)

    @Provides
    @Singleton
    fun provideMackenClient(service: MackenService): MackenClient = MackenClient(service)
}