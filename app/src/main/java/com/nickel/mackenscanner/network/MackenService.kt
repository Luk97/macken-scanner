package com.nickel.mackenscanner.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

internal interface MackenService {

    companion object {
        const val MACKEN_URL = "http://statistik.hanomacke.de/"
    }

    @Headers("Content-Type: application/json",)
    @POST("log_scan.php")
    fun getResponse(@Body request: MackenRequestBody): Call<MackenResponse>
}