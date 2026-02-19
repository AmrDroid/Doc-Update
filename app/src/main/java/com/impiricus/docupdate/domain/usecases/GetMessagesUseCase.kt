package com.impiricus.docupdate.domain.usecases

import com.impiricus.docupdate.domain.repository.MessageRepository
import com.impiricus.docupdate.domain.models.Message
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(
    private val repository: MessageRepository
) {
    suspend operator fun invoke(): List<Message> =
        repository.getMessages()
}