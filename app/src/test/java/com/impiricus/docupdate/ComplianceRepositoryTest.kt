package com.impiricus.docupdate

import com.impiricus.docupdate.data.repository.ComplianceRepository
import com.impiricus.docupdate.domain.compliance.ComplianceEngine
import com.impiricus.docupdate.domain.models.ComplianceRule
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ComplianceRepositoryTest {

    private lateinit var repository: ComplianceRepository
    private lateinit var fakeQueue: FakeComplianceQueueStorage

    @Before
    fun setup() {

        val rules = listOf(
            ComplianceRule(
                id = "R-001",
                name = "Off-label",
                keywordsAny = listOf("off-label"),
                action = "flag",
                requiresAppend = null
            )
        )

        val engine = ComplianceEngine(rules)

        fakeQueue = FakeComplianceQueueStorage()

        repository = ComplianceRepository(
            engine = engine,
            queueStorage = fakeQueue
        )
    }

    @Test
    fun `offline queues action`() = runBlocking {

        repository.isOnline = false

        repository.runCompliance("1", "off-label usage")

        assertEquals(1, fakeQueue.getAll().size)
    }

    @Test
    fun `replay processes and clears queue`() = runBlocking {

        repository.isOnline = false
        repository.runCompliance("1", "off-label usage")

        repository.isOnline = true
        val results = repository.replayQueued()

        assertEquals(1, results.size)
        assertTrue(fakeQueue.getAll().isEmpty())
    }
}
