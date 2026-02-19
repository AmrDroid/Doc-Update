package com.impiricus.docupdate.presentation.message_detail

import com.impiricus.docupdate.domain.models.ComplianceResult
import com.impiricus.docupdate.domain.models.Message

data class MessageDetailState(
    val isLoading: Boolean = false,
    val message: Message? = null,
    val complianceResult: ComplianceResult? = null,
    val error: String? = null
)