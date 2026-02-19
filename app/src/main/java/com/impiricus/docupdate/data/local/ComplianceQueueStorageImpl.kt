package com.impiricus.docupdate.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.impiricus.docupdate.domain.compliance.ComplianceQueueStorage
import com.impiricus.docupdate.domain.models.ComplianceAction
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComplianceQueueStorageImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) : ComplianceQueueStorage {

    private val Context.dataStore by preferencesDataStore(
        name = "compliance_queue"
    )
    private val KEY = stringPreferencesKey("pending_actions")

    override suspend fun enqueue(action: ComplianceAction) {
        val current = getAll().toMutableList()
        current.add(action)
        save(current)
    }

    override suspend fun getAll(): List<ComplianceAction> {

        val jsonString = context.dataStore.data
            .map { preferences -> preferences[KEY] }
            .first()

        if (jsonString.isNullOrEmpty()) return emptyList()

        val jsonArray = JSONArray(jsonString)

        return (0 until jsonArray.length()).map { index ->
            val obj = jsonArray.getJSONObject(index)
            ComplianceAction(
                messageId = obj.getString("messageId"),
                messageText = obj.getString("messageText"),
                timestamp = obj.getLong("timestamp")
            )
        }
    }

    override suspend fun clear() {
        context.dataStore.edit { preferences ->
            preferences.remove(KEY)
        }
    }

    private suspend fun save(list: List<ComplianceAction>) {

        val jsonArray = JSONArray()

        list.forEach { action ->
            val obj = JSONObject().apply {
                put("messageId", action.messageId)
                put("messageText", action.messageText)
                put("timestamp", action.timestamp)
            }
            jsonArray.put(obj)
        }

        context.dataStore.edit { preferences ->
            preferences[KEY] = jsonArray.toString()
        }
    }
}
