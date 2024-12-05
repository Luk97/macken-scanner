package com.nickel.mackenscanner.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class MackenRepository(private val service: MackenService) {

    suspend fun sendData(qrCode: String): MackenResponse {
        val response = suspendCoroutine { continuation ->
            val formattedDate = SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss",
                Locale.getDefault()).format(Date()
            )
            val requestBody = MackenRequestBody(
                scanTime = formattedDate,
                qrCode = qrCode,
                value = 70
            )
            val call: Call<MackenResponse> = service.getResponse(requestBody)
            call.enqueue(object : Callback<MackenResponse> {

                override fun onResponse(
                    call: Call<MackenResponse>,
                    response: Response<MackenResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        continuation.resume(responseBody)
                    } else {
                        continuation.resume(null)
                    }
                }

                override fun onFailure(call: Call<MackenResponse>, t: Throwable) {
                    continuation.resume(null)
                }
            })
        }
        return response ?: MackenResponse(false, "Unknown error")
    }
}