package com.impiricus.docupdate.data.analytics

import android.util.Log
import com.impiricus.docupdate.domain.analytics.AnalyticsTracker
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogcatAnalyticsTracker @Inject constructor() : AnalyticsTracker {

    override fun trackEvent(
        name: String,
        properties: Map<String, String>
    ) {
        Log.d(
            "Analytics",
            "Event: $name | Properties: $properties"
        )
    }
}