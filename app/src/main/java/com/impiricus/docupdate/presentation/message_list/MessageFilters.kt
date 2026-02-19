package com.impiricus.docupdate.presentation.message_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.impiricus.docupdate.domain.models.Physician
import com.impiricus.docupdate.util.DateRange

@Composable
fun MessageFilters(
    selectedPhysicianId: String?,
    selectedDateRange: DateRange,
    physicians: List<Physician>,
    onPhysicianSelected: (String?) -> Unit,
    onDateRangeSelected: (DateRange) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {

        PhysicianDropdown(
            physicians = physicians,
            selectedId = selectedPhysicianId,
            onSelected = onPhysicianSelected
        )

        Spacer(modifier = Modifier.height(8.dp))

        DateRangeDropdown(
            selected = selectedDateRange,
            onSelected = onDateRangeSelected
        )
    }
}
