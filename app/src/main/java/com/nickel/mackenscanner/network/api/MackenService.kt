package com.nickel.mackenscanner.network.api

import com.nickel.mackenscanner.network.dao.RemainingCodesResultDao
import com.nickel.mackenscanner.network.dao.ScanLoggedResultDao
import com.nickel.mackenscanner.network.hanomacke.MackenRequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

internal interface MackenService {

    companion object {
        const val MACKEN_URL = "https://statistik.hanomacke.de/"
        const val MACKEN_USER_NAME = "macke"
    }

    @Headers("Content-Type: application/json")
    @POST("/log_scan.php")
    suspend fun logScan(@Body request: MackenRequestBody): Response<ScanLoggedResultDao>

    @Headers("Content-Type: application/json")
    @GET("/codes_left.php")
    suspend fun requestRemainingCodes(): Response<RemainingCodesResultDao>
}