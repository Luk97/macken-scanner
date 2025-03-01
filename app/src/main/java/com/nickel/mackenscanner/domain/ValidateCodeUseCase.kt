package com.nickel.mackenscanner.domain

import com.nickel.mackenscanner.data.ScanValidationResult
import com.nickel.mackenscanner.network.hanomacke.MackenRepository

internal class ValidateCodeUseCase(
    private val mackenRepository: MackenRepository
) {

    suspend operator fun invoke(
        qrCode: String,
        onSuccess: (ScanValidationResult) -> Unit = {},
        onError: (ScanValidationResult) -> Unit = {}
    ) {
        val scanValidationResult = this.mackenRepository.validateScan(qrCode, false)

        if (scanValidationResult.success) {
            onSuccess(scanValidationResult)
        } else {
            onError(scanValidationResult)
        }
    }


}