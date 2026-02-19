package com.impiricus.docupdate.presentation.message_list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.impiricus.DocUpdate.R
import com.impiricus.docupdate.util.DateRange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangeDropdown(
    selected: DateRange,
    onSelected: (DateRange) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val label = when (selected) {
        DateRange.Last7Days -> stringResource(R.string.last_7_days)
        DateRange.Last30Days -> stringResource(R.string.last_30_days)
        DateRange.Last365Days -> stringResource(R.string.last_365_days)
        DateRange.All -> stringResource(R.string.all_time)
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = label,
            onValueChange = {},
            readOnly = true,
            label = { Text(stringResource(R.string.date_range)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth().semantics {
                    contentDescription = "Date range filter, currently $label"
                }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(R.string.last_seven_days)) },
                onClick = {
                    onSelected(DateRange.Last7Days)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text(stringResource(R.string.last_30_day)) },
                onClick = {
                    onSelected(DateRange.Last30Days)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text(stringResource(R.string.last_365_day)) },
                onClick = {
                    onSelected(DateRange.Last365Days)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text(stringResource(R.string.all_times)) },
                onClick = {
                    onSelected(DateRange.All)
                    expanded = false
                }
            )
        }
    }
}
