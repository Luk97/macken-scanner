package com.nickel.mackenscanner.domain

import com.nickel.mackenscanner.network.hanomacke.MackenRepository
import com.nickel.mackenscanner.network.wasteside.WasteSideRepository

internal class HandleQrCodeUseCase(
    private val mackenRepository: MackenRepository,
    private val wasteSideRepository: WasteSideRepository
) {

    suspend operator fun invoke(
        qrCode: String,
        onSuccess: () -> Unit = {},
        onError: () -> Unit = {}
    ) {
        val isValidQrCode = this.wasteSideRepository.validateQrCode()

        val result = if (isValidQrCode) {
            this.mackenRepository.sendData(qrCode).success
        } else {
            false
        }

        if (result) onSuccess() else onError()
    }
}