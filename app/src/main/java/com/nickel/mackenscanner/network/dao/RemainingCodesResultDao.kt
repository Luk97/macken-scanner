package com.nickel.mackenscanner.network.dao

import com.google.gson.annotations.SerializedName

internal data class RemainingCodesResultDao(
    @SerializedName("unscanned_codes")
    val remainingCodes: Int
)