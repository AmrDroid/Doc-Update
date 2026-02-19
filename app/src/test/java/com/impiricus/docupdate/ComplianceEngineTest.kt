package com.impiricus.docupdate

import com.impiricus.docupdate.domain.compliance.ComplianceEngine
import com.impiricus.docupdate.domain.models.ComplianceRule
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ComplianceEngineTest {

    private lateinit var engine: ComplianceEngine

    @Before
    fun setup() {
        val rules = listOf(
            ComplianceRule(
                id = "R-001",
                name = "No off-label",
                keywordsAny = listOf("off-label"),
                action = "flag",
                requiresAppend = null
            ),
            ComplianceRule(
                id = "R-002",
                name = "Dosing rule",
                keywordsAny = listOf("dosing", "titration"),
                action = null,
                requiresAppend = "See PI"
            )
        )

        engine = ComplianceEngine(rules)
    }

    @Test
    fun `off-label triggers R-001`() {
        val result = engine.evaluate("This is off-label usage")

        assertEquals(1, result.triggeredRules.size)
        assertEquals("R-001", result.triggeredRules.first().id)
    }

    @Test
    fun `multiple keywords trigger multiple rules`() {
        val result = engine.evaluate("off-label dosing required")

        assertEquals(2, result.triggeredRules.size)
    }

    @Test
    fun `no keywords returns empty result`() {
        val result = engine.evaluate("Normal conversation")

        assertTrue(result.triggeredRules.isEmpty())
    }

    @Test
    fun `case insensitive matching works`() {
        val result = engine.evaluate("OFF-LABEL use")

        assertEquals(1, result.triggeredRules.size)
    }
}
