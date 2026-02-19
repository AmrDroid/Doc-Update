package com.impiricus.docupdate.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.impiricus.docupdate.data.local.LocalDataSource
import com.impiricus.docupdate.domain.compliance.ComplianceEngine
import com.impiricus.docupdate.domain.models.ComplianceRule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ComplianceModule {

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun provideComplianceRules(
        localDataSource: LocalDataSource
    ): List<ComplianceRule> {
        return localDataSource.loadComplianceRules()
    }

    @Provides
    @Singleton
    fun provideComplianceEngine(
        rules: List<ComplianceRule>
    ): ComplianceEngine {
        return ComplianceEngine(rules)
    }
}