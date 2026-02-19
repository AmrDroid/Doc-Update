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
import com.impiricus.DocUpdate.R
import com.impiricus.docupdate.domain.models.Physician

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhysicianDropdown(
    physicians: List<Physician>,
    selectedId: String?,
    onSelected: (String?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val selectedName = physicians
        .find { it.id == selectedId }
        ?.fullName ?: stringResource(R.string.all_physicians)

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedName,
            onValueChange = {},
            readOnly = true,
            label = { Text(stringResource(R.string.physician)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(R.string.all_physicians)) },
                onClick = {
                    onSelected(null)
                    expanded = false
                }
            )

            physicians.forEach { physician ->
                DropdownMenuItem(
                    text = { Text(physician.fullName) },
                    onClick = {
                        onSelected(physician.id)
                        expanded = false
                    }
                )
            }
        }
    }
}
