package com.impiricus.docupdate.domain.usecases

import com.impiricus.docupdate.domain.repository.MessageRepository
import com.impiricus.docupdate.domain.models.Message
import javax.inject.Inject

class GetMessageByIdUseCase @Inject constructor(
    private val repository: MessageRepository
) {
    suspend operator fun invoke(id: String): Message? {
        return repository.getMessages().find { it.id == id }
    }
}