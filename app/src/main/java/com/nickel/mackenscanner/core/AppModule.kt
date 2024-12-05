package com.nickel.mackenscanner.core

import com.nickel.mackenscanner.domain.HandleQrCodeUseCase
import com.nickel.mackenscanner.network.hanomacke.MackenRepository
import com.nickel.mackenscanner.network.wasteside.WasteSideRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {

    @Provides
    @Singleton
    fun provideHandleQrCodeUseCase(
        mackenRepository: MackenRepository,
        wasteSideRepository: WasteSideRepository,
    ): HandleQrCodeUseCase =
        HandleQrCodeUseCase(mackenRepository, wasteSideRepository)
}