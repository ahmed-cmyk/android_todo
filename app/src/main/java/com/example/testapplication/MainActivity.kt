package com.example.testapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testapplication.data.AppDatabase
import com.example.testapplication.data.entity.Todo
import com.example.testapplication.data.repository.TodoRepository
import com.example.testapplication.ui.theme.TestApplicationTheme
import com.example.testapplication.ui.viewModel.TodoViewModel
import com.example.testapplication.ui.viewModel.TodoViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Manual DI chain
        val database = AppDatabase.getInstance(this)
        val repository = TodoRepository(database.todoDao())
        val factory = TodoViewModelFactory(repository)

        enableEdgeToEdge()
        setContent {
            val vm: TodoViewModel = viewModel(factory = factory)

            TestApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TodoScreen(
                        vm = vm,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TodoScreen(
    vm: TodoViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val state by vm.uiState.collectAsState()

    Column(modifier = modifier.padding(vertical = 24.dp, horizontal = 16.dp)) {
        TodoInput(
            createTodo = { title ->
                vm.addTodo(title)
            }
        )
        LazyColumn {
            items(state.todos) { todo ->
                Text(todo.title)
            }
        }
    }
}

@Composable
fun TodoInput(createTodo: (String) -> Unit) {
    Column (modifier = Modifier.padding(16.dp)) {
        var title by remember { mutableStateOf("") }
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text(title) },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                if (title.isNotBlank()) {
                    createTodo(title)
                    title = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Todo")
        }
    }
}

@Composable
fun TodoCard(todo: Todo, onCheckChange: (String, Boolean) -> Unit ) {
    Row() {
//        Checkbox(
//            checked = todo.completedAt != null
//        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestApplicationTheme {
        TodoScreen()
    }
}