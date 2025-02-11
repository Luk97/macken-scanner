package com.nickel.mackenscanner.network.hanomacke

import com.google.gson.annotations.SerializedName

/**
 * Request object for the Macken API.
 *
 * @param scanTime The timestamp (YYYY-MM-DD HH:MM:SS) when the scan occurred.
 * @param qrCode The QR code identifier.
 * @param value The recorded value of the scanned item (in Cents).
 * @param checkOnly If true, only validates the code without inserting it to the used codes.
 */
internal data class MackenRequestBody(
    @SerializedName("scan_time")
    val scanTime: String,
    @SerializedName("qr_code")
    val qrCode: String,
    @SerializedName("value")
    val value: Int,
    @SerializedName("check_only")
    val checkOnly: Boolean
)