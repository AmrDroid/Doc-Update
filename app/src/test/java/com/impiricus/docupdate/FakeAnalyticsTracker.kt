package com.impiricus.docupdate

import com.impiricus.docupdate.domain.analytics.AnalyticsTracker

class FakeAnalyticsTracker : AnalyticsTracker {

    val events = mutableListOf<Pair<String, Map<String, String>>>()

    override fun trackEvent(
        name: String,
        properties: Map<String, String>
    ) {
        events.add(name to properties)
    }
}
