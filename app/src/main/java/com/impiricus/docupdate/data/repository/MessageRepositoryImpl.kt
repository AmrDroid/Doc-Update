package com.impiricus.docupdate.data.repository


import android.os.Build
import androidx.annotation.RequiresApi
import com.impiricus.docupdate.data.local.LocalDataSource
import com.impiricus.docupdate.domain.models.Message
import com.impiricus.docupdate.domain.models.Physician
import com.impiricus.docupdate.domain.repository.MessageRepository
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class MessageRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : MessageRepository {

    //in memory cache we can move it to any local database
    private var cachedMessages: List<Message>? = null
    private var cachedPhysicians: List<Physician>? = null

    override suspend fun getMessages(): List<Message> {
        if (cachedMessages == null) {
            cachedMessages = localDataSource.loadMessages()
        }
        return cachedMessages.orEmpty()
    }

    override suspend fun getPhysicians(): List<Physician> {
        if (cachedPhysicians == null) {
            cachedPhysicians = localDataSource.loadPhysicians()
        }
        return cachedPhysicians.orEmpty()
    }
}