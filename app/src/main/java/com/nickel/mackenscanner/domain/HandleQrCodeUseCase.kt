package com.nickel.mackenscanner.domain

import com.nickel.mackenscanner.network.hanomacke.MackenRepository

internal class HandleQrCodeUseCase(
    private val mackenRepository: MackenRepository
) {

    suspend operator fun invoke(
        qrCode: String,
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        val scanValidationResult = this.mackenRepository.validateScan(qrCode, true)

        if (scanValidationResult.success) {
            onSuccess()
        } else {
            onError("WasteSide Code invalid")
        }
    }


}