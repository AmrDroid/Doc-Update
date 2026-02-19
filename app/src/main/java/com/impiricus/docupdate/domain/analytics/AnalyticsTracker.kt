package com.impiricus.docupdate.domain.analytics

interface AnalyticsTracker {

    fun trackEvent(
        name: String,
        properties: Map<String, String> = emptyMap()
    )
}