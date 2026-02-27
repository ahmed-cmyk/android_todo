package com.example.todoApplication.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.todoApplication.R
import com.example.todoApplication.data.entity.Todo

@Composable
fun TodoCard(
    todo: Todo,
    onCheckAction: (Int, Boolean) -> Unit,
    onDeleteAction: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = todo.completedAt != null,
                onCheckedChange = { checked: Boolean ->
                    onCheckAction(todo.id, checked)
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = todo.title)
            IconButton(onClick = { onDeleteAction(todo.id) }) {
                Icon(
                    painter = painterResource(R.drawable.delete),
                    contentDescription = "Delete Todo",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}