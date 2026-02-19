package com.impiricus.docupdate.data.repository

import com.impiricus.docupdate.domain.compliance.ComplianceEngine
import com.impiricus.docupdate.domain.compliance.ComplianceQueueStorage
import com.impiricus.docupdate.domain.models.ComplianceAction
import com.impiricus.docupdate.domain.models.ComplianceResult
import javax.inject.Inject

class ComplianceRepository @Inject constructor(
    private val engine: ComplianceEngine,
    private val queueStorage: ComplianceQueueStorage
) {
    var isOnline: Boolean = true
    suspend fun runCompliance(
        messageId: String,
        text: String
    ): ComplianceResult {

        return if (isOnline) {
            engine.evaluate(text)
        } else {
            queueStorage.enqueue(
                ComplianceAction(
                    messageId = messageId,
                    messageText = text,
                    timestamp = System.currentTimeMillis()
                )
            )
            ComplianceResult(emptyList())
        }
    }

    suspend fun replayQueued(): List<ComplianceResult> {

        val queued = queueStorage.getAll()

        val results = queued.map {
            engine.evaluate(it.messageText)
        }

        queueStorage.clear()

        return results
    }
}
