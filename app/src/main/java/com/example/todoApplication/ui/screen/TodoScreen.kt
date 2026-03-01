package com.example.todoApplication.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoApplication.R
import com.example.todoApplication.ui.component.AddTodoDialog
import com.example.todoApplication.ui.component.TodoFilterHeader
import com.example.todoApplication.ui.component.TodoList
import com.example.todoApplication.ui.viewModel.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(vm: TodoViewModel = viewModel()) {
    val state by vm.uiState.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.statusBarsPadding()
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    painter = painterResource(R.drawable.addbox),
                    contentDescription = "Add Todo",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        modifier = Modifier.padding(16.dp)
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

            TodoFilterHeader(
                selectedFilter = state.selectedFilter,
                onFilterSelected = { newFilter ->
                    vm.setFilter(newFilter)
                }
            )

            TodoList(
                todos = state.todos,
                onCheckAction = { id, completed ->
                    vm.toggleCompleted(id, completed)
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