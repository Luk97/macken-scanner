package com.nickel.mackenscanner.network

import com.google.gson.annotations.SerializedName

data class MackenResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String
)