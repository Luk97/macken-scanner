package com.nickel.mackenscanner.network.hanomacke

import android.util.Log
import com.nickel.mackenscanner.data.ScanValidationResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class MackenRepository(private val service: MackenService) {

    suspend fun validateScan(qrCode: String, checkOnly: Boolean): ScanValidationResult {
        val request = getRequestBody(qrCode, checkOnly)
        val dao = requestScanCodeValidation(request)
        return dao?.toScanValidationResult() ?: ScanValidationResult(
            success = false,
            message = "unknown error"
        )
    }

    private fun getRequestBody(qrCode: String, checkOnly: Boolean): MackenRequestBody {
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

    private suspend fun requestScanCodeValidation(request: MackenRequestBody): ScanLoggedResultDao? =
        suspendCoroutine { continuation ->
            val call: Call<ScanLoggedResultDao> = service.getResponse(request)
            call.enqueue(object : Callback<ScanLoggedResultDao> {

                override fun onResponse(
                    call: Call<ScanLoggedResultDao>,
                    response: Response<ScanLoggedResultDao>
                ) {
                    Log.d("TAG", "Response: ${response.body()}")

                    if (response.isSuccessful) {
                        response.body()?.let { dao ->
                            continuation.resume(dao)
                        }
                    } else {
                        continuation.resume(null)
                    }
                }

                override fun onFailure(call: Call<ScanLoggedResultDao>, t: Throwable) {
                    continuation.resume(null)
                }
            })
    }

    private fun ScanLoggedResultDao.toScanValidationResult(): ScanValidationResult =
        when {
            success == "code_logged" -> ScanValidationResult(
                success = true,
                message = "Code valide. Erfolgreich abgespeichert."
            )
            success == "code_valid" -> ScanValidationResult(
                success = true,
                message = "Code valide, nicht abgespeichert."
            )
            else -> ScanValidationResult(
                success = false,
                message = "unknown error"
            )
        }
}