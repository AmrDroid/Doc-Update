package com.impiricus.docupdate.domain.usecases

import com.impiricus.docupdate.domain.repository.MessageRepository
import com.impiricus.docupdate.domain.models.Physician
import javax.inject.Inject

class GetPhysiciansUseCase @Inject constructor(
    private val repository: MessageRepository
) {
    suspend operator fun invoke(): List<Physician> =
        repository.getPhysicians()
}