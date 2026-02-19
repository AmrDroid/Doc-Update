package com.impiricus.docupdate.presentation.message_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.impiricus.DocUpdate.R

@Composable
fun MessageRow(
    message: MessageUiModel,
    onClick: () -> Unit
) {
    val sentimentColor = when (message.sentiment.lowercase()) {
        stringResource(R.string.positive) -> MaterialTheme.colorScheme.primary
        stringResource(R.string.negative) -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.onSurface
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .semantics(mergeDescendants = true) {
                contentDescription =
                    "Message about ${message.topic}, " +
                            "from ${message.physicianName}, " +
                            "sentiment ${message.sentiment}, " +
                            "sent on ${message.formattedDate}"
            }
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Text(
                text = message.topic,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Physician: ${message.physicianName}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Sentiment: ${message.sentiment}",
                style = MaterialTheme.typography.bodySmall,
                color = sentimentColor
            )

            Text(
                text = message.formattedDate,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
