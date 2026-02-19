package com.impiricus.docupdate.util

sealed class DateRange {

    object Last7Days : DateRange()
    object Last30Days : DateRange()
    object Last365Days : DateRange()
    object All : DateRange()

    fun toStartTimestamp(): Long? {

        val now = System.currentTimeMillis()

        return when (this) {
            Last7Days -> now - DAYS_7
            Last30Days -> now - DAYS_30
            Last365Days -> now - DAYS_365
            All -> null
        }
    }

    companion object {
        private const val DAY_MILLIS = 24 * 60 * 60 * 1000L
        private const val DAYS_7 = 7 * DAY_MILLIS
        private const val DAYS_30 = 30 * DAY_MILLIS
        private const val DAYS_365 = 365 * DAY_MILLIS
    }
}