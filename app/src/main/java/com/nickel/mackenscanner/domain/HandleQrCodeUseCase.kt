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
        onError: (String) -> Unit = {}
    ) {
        if (this.wasteSideRepository.validateQrCode()) {
            val mackenResponse = this.mackenRepository.sendData(qrCode)
            if (mackenResponse.success) {
                onSuccess()
            } else {
                onError(mackenResponse.message)
            }
        } else {
            onError("WasteSide Code invalid")
        }
    }
}