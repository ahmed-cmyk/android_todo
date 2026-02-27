package com.example.testapplication.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testapplication.ui.component.AddTodoInput
import com.example.testapplication.ui.component.TodoList
import com.example.testapplication.ui.viewModel.TodoViewModel

@Composable
fun TodoScreen(vm: TodoViewModel = viewModel()) {
    val state by vm.uiState.collectAsState()

    Scaffold(
        topBar = { Text("Todo App", modifier = Modifier.padding(16.dp)) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            AddTodoInput(
                createTodo = { title ->
                    vm.addTodo(title)
                }
            )

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
    }
}