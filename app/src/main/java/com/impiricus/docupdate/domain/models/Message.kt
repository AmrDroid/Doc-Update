package com.impiricus.docupdate.domain.models


data class Message(
    val id: String,
    val physicianId: String,
    val channel: String,
    val direction: String,
    val timestamp: Long,
    val text: String,
    val campaignId: String,
    val topic: String,
    val complianceTag: String?,
    val sentiment: String,
    val deliveryStatus: String,
    val responseLatencySec: Int
)