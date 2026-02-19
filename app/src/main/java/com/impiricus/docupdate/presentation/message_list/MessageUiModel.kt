package com.impiricus.docupdate.presentation.message_list

data class MessageUiModel(
    val id: String,
    val physicianName: String,
    val topic: String,
    val sentiment: String,
    val formattedDate: String
)
