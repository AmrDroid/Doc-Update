package com.impiricus.docupdate.util

import com.impiricus.docupdate.domain.models.Message
import com.impiricus.docupdate.domain.models.Physician
import com.impiricus.docupdate.presentation.message_list.MessageUiModel


fun mapMessagesToUiModel(
    messages: List<Message>,
    physicians: List<Physician>
): List<MessageUiModel> {

    val physicianMap = physicians.associateBy { it.id }

    return messages.map { message ->
        val physician = physicianMap[message.physicianId]

        MessageUiModel(
            id = message.id,
            physicianName = physician?.fullName ?: "Unknown",
            topic = message.topic,
            sentiment = message.sentiment,
            formattedDate = DateFormatter.format(message.timestamp)
        )
    }
}
