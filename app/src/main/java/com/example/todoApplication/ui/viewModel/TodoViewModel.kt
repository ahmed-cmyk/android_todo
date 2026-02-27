package com.example.todoApplication.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoApplication.data.entity.Todo
import com.example.todoApplication.data.repository.TodoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TodoUiState(
    val todos: List<Todo> = emptyList(),
    val showCompleted: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)

class TodoViewModel(
    private val repository: TodoRepository
): ViewModel() {

    private val showCompleted = MutableStateFlow(false)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<TodoUiState> =
        showCompleted
            .flatMapLatest { completed ->
                repository
                    .getByStatus(completed)
                    .map { todos ->
                        TodoUiState(
                            todos = todos,
                            showCompleted = completed,
                            isLoading = false
                        )
                    }
                    .onStart {
                        emit(
                            TodoUiState(
                                isLoading = true,
                                showCompleted = completed
                            )
                        )
                    }
            }
            .catch { error ->
                emit(
                    TodoUiState(
                        error = error.message
                    )
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = TodoUiState(isLoading = true)
            )

    fun toggleCompletedFilter() {
        showCompleted.update { !it }
    }

    fun addTodo(title: String) {
        viewModelScope.launch {
            repository.createTodo(title)
        }
    }

    fun toggleCompleted(id: Int, completed: Boolean) {
        viewModelScope.launch {
            repository.toggleCompleted(id, completed)
        }
    }

    fun updateTitle(id: Int, title: String) {
        viewModelScope.launch {
            repository.updateTitle(id, title);
        }
    }

    fun deleteTodo(id: Int) {
        viewModelScope.launch {
            repository.deleteTodo(id)
        }
    }
}

class TodoViewModelFactory(private val repository: TodoRepository): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            return TodoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}