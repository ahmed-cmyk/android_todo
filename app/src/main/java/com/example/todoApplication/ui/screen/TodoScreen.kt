package com.example.todoApplication.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoApplication.R
import com.example.todoApplication.ui.component.AddTodoDialog
import com.example.todoApplication.ui.component.TodoList
import com.example.todoApplication.ui.viewModel.TodoViewModel

@Composable
fun TodoScreen(vm: TodoViewModel = viewModel()) {
    val state by vm.uiState.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { Text("Todo App", modifier = Modifier.padding(16.dp)) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    painter = painterResource(R.drawable.addbox),
                    contentDescription = "Add Todo",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(32.dp) // make the icon bigger
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TodoList(
                todos = state.todos,
                onCheckAction = { id, checked ->
                    vm.toggleCompleted(id, checked)
                },
                onDeleteAction = { id ->
                    vm.deleteTodo(id)
                }
            )
        }

        if (showAddDialog) {
            AddTodoDialog(
                onAdd = { title ->
                    vm.addTodo(title)
                },
                onDismiss = { showAddDialog = false }
            )
        }
    }
}