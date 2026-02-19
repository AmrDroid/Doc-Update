package com.impiricus.docupdate

import com.impiricus.docupdate.domain.compliance.ComplianceQueueStorage
import com.impiricus.docupdate.domain.models.ComplianceAction

class FakeComplianceQueueStorage : ComplianceQueueStorage {

    private val storage = mutableListOf<ComplianceAction>()

    override suspend fun enqueue(action: ComplianceAction) {
        storage.add(action)
    }

    override suspend fun getAll(): List<ComplianceAction> {
        return storage.toList()
    }

    override suspend fun clear() {
        storage.clear()
    }
}
