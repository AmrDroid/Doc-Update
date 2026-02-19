package com.impiricus.docupdate.domain.models

data class ComplianceRule(
    val id: String,
    val name: String,
    val keywordsAny: List<String>,
    val action: String?,
    val requiresAppend: String?
)