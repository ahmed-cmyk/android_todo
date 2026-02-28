package com.example.todoApplication.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todoApplication.ui.viewModel.TodoFilter

@Composable
fun TodoFilterHeader(
    selectedFilter: TodoFilter,
    onFilterSelected: (TodoFilter) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        SingleChoiceSegmentedButtonRow {
            TodoFilter.entries.forEachIndexed { index, filter ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = TodoFilter.entries.size
                    ),
                    onClick = {
                        onFilterSelected(filter)
                    },
                    selected = filter == selectedFilter,
                    label = { Text(
                        text = filter.label,
                        style = MaterialTheme.typography.labelLarge
                    ) }
                )
            }
        }
    }
}