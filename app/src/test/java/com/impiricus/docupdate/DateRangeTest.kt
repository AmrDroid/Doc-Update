package com.impiricus.docupdate

import com.impiricus.docupdate.util.DateRange
import org.junit.Assert.*
import org.junit.Test

class DateRangeTest {

    @Test
    fun `last 7 days subtracts correct millis`() {

        val now = System.currentTimeMillis()
        val start = DateRange.Last7Days.toStartTimestamp()!!

        val diff = now - start

        val sevenDaysMillis = 7 * 24 * 60 * 60 * 1000L

        assertTrue(diff in (sevenDaysMillis - 1000)..(sevenDaysMillis + 1000))
    }
}
