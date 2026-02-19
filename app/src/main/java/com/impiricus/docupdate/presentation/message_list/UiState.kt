package com.impiricus.docupdate.presentation.message_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.impiricus.DocUpdate.R

@Composable
fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.padding(24.dp))
    }
}

@Composable
fun ErrorState(message: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = message,
            modifier = Modifier.padding(24.dp)
        )
    }
}

@Composable
fun EmptyState() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.no_messages_found),
            modifier = Modifier.padding(24.dp).semantics {
                contentDescription = "No engagement messages available."
            }
        )
    }
}
