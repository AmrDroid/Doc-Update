package com.impiricus.docupdate.di

import com.impiricus.docupdate.data.local.ComplianceQueueStorageImpl
import com.impiricus.docupdate.domain.compliance.ComplianceQueueStorage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ComplianceStorageModule {

    @Binds
    @Singleton
    abstract fun bindComplianceQueueStorage(
        impl: ComplianceQueueStorageImpl
    ): ComplianceQueueStorage
}
