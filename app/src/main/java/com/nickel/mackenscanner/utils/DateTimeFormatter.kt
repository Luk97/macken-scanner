package com.nickel.mackenscanner.utils

import java.text.SimpleDateFormat
import java.util.Locale

internal object DateTimeFormatter {

    fun convertDateTimeFormat(input: String?): String? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())

        val date = input?.let { inputFormat.parse(it) } ?: return input
        return outputFormat.format(date)
    }
}