package com.impiricus.docupdate.presentation.message_list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageListScreen(
    navController: NavController,
    viewModel: MessageListViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Engagement Messages") },
                modifier = Modifier.semantics {
                    contentDescription = "Engagement Messages"
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            // FILTERS
            MessageFilters(
                selectedPhysicianId = state.selectedPhysicianId,
                selectedDateRange = state.dateFilter,
                physicians = state.physicians,
                onPhysicianSelected = viewModel::onPhysicianSelected,
                onDateRangeSelected = viewModel::onDateRangeSelected
            )

            HorizontalDivider()

            // CONTENT
            when {
                state.isLoading -> LoadingState()
                state.error != null -> ErrorState(state.error ?: "")
                state.messages.isEmpty() -> EmptyState()
                else -> MessageList(
                    messages = state.messages,
                    onMessageClick = { id ->
                        navController.navigate("detail/$id")
                    }
                )
            }
        }
    }
}
