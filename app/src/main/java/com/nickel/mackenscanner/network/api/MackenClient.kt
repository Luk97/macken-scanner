package com.nickel.mackenscanner.network.api

import android.util.Log
import com.nickel.mackenscanner.network.dao.RemainingCodesResultDao
import com.nickel.mackenscanner.network.dao.ScanLoggedResultDao
import com.nickel.mackenscanner.network.hanomacke.MackenRequestBody

internal class MackenClient(private val service: MackenService) {

    private companion object {
        const val TAG = "macken-client"
    }

    suspend fun requestScanCodeValidation(request: MackenRequestBody): ScanLoggedResultDao? =
        try {
            val response = service.logScan(request)
            if (response.isSuccessful) {
                Log.d(TAG, response.body().toString())
                response.body()
            } else {
                Log.e(TAG, response.errorBody()?.string() ?: "requestScanCodeValidation failed")
                null
            }
        } catch (e: Exception) {
            Log.e("TAG", "Error: ${e.message}", e)
            null
        }

    suspend fun requestRemainingCodes(): RemainingCodesResultDao? =
        try {
            val response = service.requestRemainingCodes()
            if (response.isSuccessful) {
                Log.d(TAG, response.body().toString())
                response.body()
            } else {
                Log.e(TAG, response.errorBody()?.string() ?: "requestRemainingCodes failed")
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error: ${e.message}", e)
            null
        }
}