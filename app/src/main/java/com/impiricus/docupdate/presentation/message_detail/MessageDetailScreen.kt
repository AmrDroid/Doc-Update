package com.impiricus.docupdate.presentation.message_detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MessageDetailScreen(
    id: String,
    viewModel: MessageDetailViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadMessage(id)
    }

    when {
        state.isLoading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(24.dp)
                        .semantics {
                            contentDescription = "Loading messages"
                        }
                )
            }
        }

        state.error != null -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = state.error ?: "",
                    modifier = Modifier.padding(24.dp)
                )
            }
        }

        state.message != null -> {
            MessageDetailContent(
                state = state,
                onRunCompliance = { viewModel.runComplianceCheck() }
            )
        }
    }
}