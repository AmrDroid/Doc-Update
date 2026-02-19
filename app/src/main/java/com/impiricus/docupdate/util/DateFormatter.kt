package com.impiricus.docupdate.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateFormatter {

    private val formatter = SimpleDateFormat(
        "MMM dd, yyyy HH:mm",
        Locale.getDefault()
    )

    fun format(timestamp: Long): String {
        return formatter.format(Date(timestamp))
    }
}