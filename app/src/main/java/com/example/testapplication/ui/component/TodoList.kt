package com.example.testapplication.ui.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.testapplication.data.entity.Todo

@Composable
fun TodoList(
    todos: List<Todo>,
    onCheckAction: (Int, Boolean) -> Unit,
    onDeleteAction: (Int) -> Unit
) {
    LazyColumn {
        items(todos) { todo ->
            TodoCard(
                todo = todo,
                onCheckAction = onCheckAction,
                onDeleteAction = onDeleteAction
            )
        }
    }
}