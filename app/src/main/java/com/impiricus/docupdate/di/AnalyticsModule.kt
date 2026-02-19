package com.impiricus.docupdate.di

import com.impiricus.docupdate.data.analytics.LogcatAnalyticsTracker
import com.impiricus.docupdate.domain.analytics.AnalyticsTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AnalyticsModule {

    @Binds
    @Singleton
    abstract fun bindAnalyticsTracker(
        impl: LogcatAnalyticsTracker
    ): AnalyticsTracker
}
