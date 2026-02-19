package com.impiricus.docupdate.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateFormatter {
    fun format(timestamp: Long): String {
        return SimpleDateFormat(
            "MMM dd, yyyy HH:mm",
            Locale.getDefault()
        ).format(Date(timestamp))
    }
}