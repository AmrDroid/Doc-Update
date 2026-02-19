package com.impiricus.docupdate.domain.compliance

import com.impiricus.docupdate.domain.models.ComplianceAction


interface ComplianceQueueStorage {

    suspend fun enqueue(action: ComplianceAction)

    suspend fun getAll(): List<ComplianceAction>

    suspend fun clear()
}
