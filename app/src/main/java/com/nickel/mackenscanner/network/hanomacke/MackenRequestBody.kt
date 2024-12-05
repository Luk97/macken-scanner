package com.nickel.mackenscanner.network.hanomacke

import com.google.gson.annotations.SerializedName

internal data class MackenRequestBody(
    @SerializedName("scan_time")
    val scanTime: String,
    @SerializedName("qr_code")
    val qrCode: String,
    @SerializedName("value")
    val value: Int
)