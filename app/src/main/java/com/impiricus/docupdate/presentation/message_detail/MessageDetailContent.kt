package com.impiricus.docupdate.presentation.message_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
fun MessageDetailContent(
    state: MessageDetailState,
    onRunCompliance: () -> Unit
) {
    val message = state.message

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp, bottom = 16.dp, end = 16.dp, start = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = stringResource(R.string.topic, message?.topic ?: ""),
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.sentiment, message?.sentiment ?: ""),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = message?.text ?: "",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(
                R.string.existing_compliance_tag, message?.complianceTag ?: stringResource(
                    R.string.none
                )
            ),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onRunCompliance,
            modifier = Modifier.fillMaxWidth() .semantics {
                contentDescription = "Run compliance check for this message"
            }

        ) {
            Text(stringResource(R.string.run_compliance_check))
        }

        Spacer(modifier = Modifier.height(24.dp))

        state.complianceResult?.let { result ->

            Text(
                text = stringResource(R.string.compliance_results),
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (result.triggeredRules.isEmpty()) {
                Text(stringResource(R.string.no_rules_triggered))
            } else {
                result.triggeredRules.forEach { rule ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(stringResource(R.string.rule, rule.name))
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(stringResource(R.string.reason, rule.reason))
                        }
                    }
                }
            }
        }
    }
}
