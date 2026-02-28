package com.example.todoApplication.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun TodoFilterHeader(toggleFilter: () -> Unit) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val options = listOf("pending", "completed")
    val currentDate: LocalDate = LocalDate.now()

    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            val dayOfWeek = currentDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
            val dayOfMonth = currentDate.dayOfMonth
            val month = currentDate.month.getDisplayName(TextStyle.SHORT, Locale.getDefault())

            Text(
                text = "$dayOfWeek, $month $dayOfMonth",
                style = MaterialTheme.typography.headlineSmall
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        SingleChoiceSegmentedButtonRow {
            options.forEachIndexed { index, label ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = options.size
                    ),
                    onClick = {
                        // Ensure toggle runs when index actually changes
                        if (selectedIndex != index) {
                            selectedIndex = index
                            toggleFilter()
                        }
                    },
                    selected = index == selectedIndex,
                    label = { Text(label) }
                )
            }
        }
    }
}