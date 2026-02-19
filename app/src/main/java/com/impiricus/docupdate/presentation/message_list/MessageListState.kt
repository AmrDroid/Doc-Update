package com.impiricus.docupdate.presentation.message_list

import com.impiricus.docupdate.domain.models.Physician
import com.impiricus.docupdate.util.DateRange

data class MessageListState(
    val messages: List<MessageUiModel> = emptyList(),
    val physicians: List<Physician> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedPhysicianId: String? = null,
    val dateFilter: DateRange = DateRange.All
)