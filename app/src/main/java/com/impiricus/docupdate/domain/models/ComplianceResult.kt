package com.impiricus.docupdate.domain.models


data class ComplianceResult(
    val triggeredRules: List<TriggeredRule>
)

data class TriggeredRule(
    val id: String,
    val name: String,
    val reason: String
)