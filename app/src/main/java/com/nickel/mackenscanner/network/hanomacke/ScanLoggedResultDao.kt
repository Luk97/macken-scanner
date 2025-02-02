package com.nickel.mackenscanner.network.hanomacke

import com.google.gson.annotations.SerializedName

data class ScanLoggedResultDao(
    @SerializedName("success")
    val success: String?,
    @SerializedName("error")
    val error: String?,
    @SerializedName("first_scan_time")
    val firstScanTime: String?
)