package com.example.todoApplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoApplication.data.AppDatabase
import com.example.todoApplication.data.repository.TodoRepository
import com.example.todoApplication.ui.screen.TodoScreen
import com.example.todoApplication.ui.theme.TodoApplicationTheme
import com.example.todoApplication.ui.viewModel.TodoViewModel
import com.example.todoApplication.ui.viewModel.TodoViewModelFactory

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

            TodoApplicationTheme {
                TodoScreen(vm)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoPreview() {
    TodoApplicationTheme {
        TodoScreen()
    }
}