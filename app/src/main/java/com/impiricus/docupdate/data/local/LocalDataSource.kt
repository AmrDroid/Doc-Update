package com.impiricus.docupdate.data.local

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.impiricus.docupdate.domain.models.ComplianceRule
import com.impiricus.docupdate.domain.models.Message
import com.impiricus.docupdate.domain.models.Physician
import com.opencsv.CSVReader
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONObject
import javax.inject.Inject
import java.io.InputStreamReader
import java.time.LocalDateTime
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
class LocalDataSource @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    fun loadPhysicians(): List<Physician> {
        val inputStream = context.assets.open("physicians.csv")
        return CSVReader(InputStreamReader(inputStream)).use { reader ->
            reader.readAll()
                .drop(1) // skip header
                .map { row ->

                    Physician(
                        id = row[0],
                        npi = row[1],
                        firstName = row[2],
                        lastName = row[3],
                        specialty = row[4],
                        state = row[5],
                        consentOptIn = row[6].toBooleanStrictOrNull() ?: false,
                        preferredChannel = row[7]
                    )
                }
        }
    }

    fun loadMessages(): List<Message> {
        val inputStream = context.assets.open("messages.csv")
        return CSVReader(InputStreamReader(inputStream)).use { reader ->
            reader.readAll()
                .drop(1)
                .map { row ->
                    val timestamp = LocalDateTime.parse(row[4])
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
                        .toEpochMilli()
                    Message(
                        id = row[0],
                        physicianId = row[1],
                        channel = row[2],
                        direction = row[3],
                        timestamp = timestamp,
                        text = row[5],
                        campaignId = row[6],
                        topic = row[7],
                        complianceTag = row[8].takeIf { it.isNotBlank() },
                        sentiment = row[9],
                        deliveryStatus = row[10],
                        responseLatencySec = row[11].toIntOrNull() ?: 0
                    )
                }
        }
    }

    fun loadComplianceRules(): List<ComplianceRule> {
        return try {
            val jsonString = context.assets
                .open("compliance_policies.json")
                .bufferedReader()
                .use { it.readText() }

            val root = JSONObject(jsonString)
            val rulesArray = root.getJSONArray("rules")

            (0 until rulesArray.length()).map { index ->
                val rule = rulesArray.getJSONObject(index)

                ComplianceRule(
                    id = rule.getString("id"),
                    name = rule.getString("name"),
                    keywordsAny = rule.getJSONArray("keywords_any")
                        .let { arr ->
                            (0 until arr.length())
                                .map { i -> arr.getString(i) }
                        },
                    action = rule.optString("action", ""),
                    requiresAppend = rule.optString("requires_append", "")
                )
            }
        } catch (_: Exception) {
            emptyList()
        }
    }
}