package com.nickel.mackenscanner.network.hanomacke

import com.nickel.mackenscanner.data.ScanValidationResult
import com.nickel.mackenscanner.network.api.MackenClient
import com.nickel.mackenscanner.network.dao.ScanLoggedResultDao
import com.nickel.mackenscanner.utils.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal class MackenRepository(private val client: MackenClient) {

    suspend fun validateScan(qrCode: String, checkOnly: Boolean): ScanValidationResult {
        val request = createMackenRequestBody(qrCode, checkOnly)
        val dao = client.requestScanCodeValidation(request)
        return createLogScanResult(dao)
    }

    suspend fun getRemainingCodeCount(): Int? {
        val dao = client.requestRemainingCodes()
        return dao?.remainingCodes
    }

    private fun createMackenRequestBody(qrCode: String, checkOnly: Boolean): MackenRequestBody {
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
                message = "Gültiger Code wurde eingelöst."
            )
            dao?.success == "code_valid" -> ScanValidationResult(
                success = true,
                message = "Gültiger Code, aber wurde noch nicht eingelöst. "
            )
            dao?.error == "already_used" -> ScanValidationResult(
                success = false,
                message = "Der Code wurde bereits am ${DateTimeFormatter.convertDateTimeFormat(dao.firstScanTime)} eingelöst."
            )
            else -> ScanValidationResult(
                success = false,
                message = "Irgendetwas ist schiefgelaufen. Probiere es nochmal."
            )
        }
}