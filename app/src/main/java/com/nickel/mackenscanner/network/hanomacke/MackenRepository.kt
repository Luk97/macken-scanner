package com.nickel.mackenscanner.network.hanomacke

import com.nickel.mackenscanner.data.ScanValidationResult
import com.nickel.mackenscanner.network.api.MackenClient
import com.nickel.mackenscanner.network.dao.ScanLoggedResultDao
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal class MackenRepository(private val client: MackenClient) {

    suspend fun validateScan(qrCode: String, checkOnly: Boolean): ScanValidationResult {
        val request = createLogScanRequest(qrCode, checkOnly)
        val dao = client.requestScanCodeValidation(request)
        return createLogScanResult(dao)
    }

    suspend fun getRemainingCodeCount(): Int? {
        val dao = client.requestRemainingCodes()
        return dao?.remainingCodes
    }

    private fun createLogScanRequest(qrCode: String, checkOnly: Boolean): MackenRequestBody {
        val formattedDate = SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss",
            Locale.getDefault()
        ).format(Date())
        return MackenRequestBody(
            scanTime = formattedDate,
            qrCode = qrCode,
            value = 70,
            checkOnly = checkOnly
        )
    }

    private fun createLogScanResult(dao: ScanLoggedResultDao?): ScanValidationResult =
        when {
            dao?.success == "code_logged" -> ScanValidationResult(
                success = true,
                message = "Code valide. Erfolgreich abgespeichert."
            )
            dao?.success == "code_valid" -> ScanValidationResult(
                success = true,
                message = "Code valide, nicht abgespeichert."
            )
            else -> ScanValidationResult(
                success = false,
                message = "unknown error"
            )
        }
}