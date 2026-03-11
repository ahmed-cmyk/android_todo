package com.example.todoApplication.ui.viewModel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoApplication.data.entity.Todo
import com.example.todoApplication.data.repository.TodoRepository
import com.example.todoApplication.datastore.Prefs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

enum class TodoFilter(val label: String) {
    ALL("All"),
    COMPLETED("Completed"),
    INCOMPLETE("Incomplete")
}

@Immutable
data class TodoUiState(
    val todos: List<Todo> = emptyList(),
    val showCompleted: Boolean = false,
    val selectedFilter: TodoFilter = TodoFilter.ALL,
    val currentDate: LocalDate = LocalDate.now(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class TodoViewModel(
    private val repository: TodoRepository,
    private val prefs: Prefs
): ViewModel() {

    val filter: Flow<TodoFilter> = prefs.selectedFilter()

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<TodoUiState> =
        combine(
            repository.allTodos,
            prefs.selectedFilter()
        ) { todos, selectedFilter ->

            val filteredTodos = when (selectedFilter) {
                TodoFilter.ALL -> todos
                TodoFilter.COMPLETED ->
                    todos.filter { it.completedAt != null }

                TodoFilter.INCOMPLETE ->
                    todos.filter { it.completedAt == null }
            }

            TodoUiState(
                todos = filteredTodos,
                selectedFilter = selectedFilter,
                currentDate = LocalDate.now(),
                isLoading = false
            )
        }
        .onStart {
            emit(
                TodoUiState(
                    isLoading = true
                )
            )
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

    fun setFilter(newFilter: TodoFilter) {
        viewModelScope.launch {
            prefs.setFilter(newFilter)
        }
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
            repository.updateTitle(id, title)
        }
    }

    fun deleteTodo(id: Int) {
        viewModelScope.launch {
            repository.deleteTodo(id)
        }
    }
}

class TodoViewModelFactory(
    private val repository: TodoRepository,
    private val prefs: Prefs
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            return TodoViewModel(repository, prefs) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}