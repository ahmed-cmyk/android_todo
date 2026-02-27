package com.example.todoApplication.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.todoApplication.data.entity.Todo

@Composable
fun TodoList(
    todos: List<Todo>,
    onCheckAction: (Int, Boolean) -> Unit,
    onDeleteAction: (Int) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(todos) { todo ->
            TodoCard(
                todo = todo,
                onCheckAction = onCheckAction,
                onDeleteAction = onDeleteAction
            )
        }
    }
}