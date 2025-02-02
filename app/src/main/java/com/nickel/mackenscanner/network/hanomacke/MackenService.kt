package com.nickel.mackenscanner.network.hanomacke

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

internal interface MackenService {

    companion object {
        const val MACKEN_URL = "https://statistik.hanomacke.de/"
        const val MACKEN_USER_NAME = "macke"
    }

    @Headers("Content-Type: application/json")
    @POST("/log_scan.php")
    fun getResponse(@Body request: MackenRequestBody): Call<ScanLoggedResultDao>
}