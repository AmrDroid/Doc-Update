package com.impiricus.docupdate.domain.compliance

import com.impiricus.docupdate.domain.models.ComplianceResult
import com.impiricus.docupdate.domain.models.ComplianceRule
import com.impiricus.docupdate.domain.models.TriggeredRule
import javax.inject.Inject

class ComplianceEngine @Inject constructor(
    private val rules: List<ComplianceRule>
) {

    fun evaluate(text: String): ComplianceResult {
        val triggered = rules.mapNotNull { rule ->
            val matched = rule.keywordsAny
                .filter { text.contains(it, ignoreCase = true) }

            if (matched.isNotEmpty()) {
                TriggeredRule(
                    id = rule.id,
                    name = rule.name,
                    reason = "Matched keywords: ${matched.joinToString()}"
                )
            } else null
        }

        return ComplianceResult(triggered)
    }
}
