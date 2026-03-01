package com.example.todoApplication.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.example.todoApplication.R

@Composable
fun AddTodoDialog(
    onAdd: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var todoText by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.add_dialog_title)) },
        text = {
            OutlinedTextField(
                value = todoText,
                onValueChange = { todoText = it },
                label = { Text(text = stringResource(id = R.string.todo_title_placeholder)) },
                singleLine = true
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (todoText.isNotBlank()) {
                        onAdd(todoText.trim())
                        todoText = ""
                        onDismiss()
                    }
                }
            ) {
                Text(text = stringResource(id = R.string.add_button))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.cancel_button))
            }
        }
    )
}