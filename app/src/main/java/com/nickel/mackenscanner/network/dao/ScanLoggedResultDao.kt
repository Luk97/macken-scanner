package com.nickel.mackenscanner.network.dao

import com.google.gson.annotations.SerializedName

internal data class ScanLoggedResultDao(
    @SerializedName("success")
    val success: String?,
    @SerializedName("error")
    val error: String?,
    @SerializedName("first_scan_time")
    val firstScanTime: String?
)