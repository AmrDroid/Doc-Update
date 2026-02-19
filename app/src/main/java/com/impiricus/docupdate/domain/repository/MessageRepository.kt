package com.impiricus.docupdate.domain.repository

import com.impiricus.docupdate.domain.models.Message
import com.impiricus.docupdate.domain.models.Physician

interface MessageRepository {
    suspend fun getMessages(): List<Message>
    suspend fun getPhysicians(): List<Physician>
}