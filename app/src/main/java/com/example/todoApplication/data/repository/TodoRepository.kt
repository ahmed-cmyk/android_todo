package com.example.todoApplication.data.repository

import com.example.todoApplication.data.dao.TodoDao
import com.example.todoApplication.data.entity.Todo
import kotlinx.coroutines.flow.Flow

class TodoRepository(private val todoDao: TodoDao) {

    val allTodos: Flow<List<Todo>> = todoDao.getAll()

    fun getByStatus(status: Boolean): Flow<List<Todo>> {
        return todoDao.getByStatus(status)
    }

    suspend fun createTodo(title: String): Long {
        return todoDao.create(Todo(
            title = title
        ))
    }

    suspend fun updateTitle(id: Int, title: String) {
        todoDao.updateTitle(id, title)
    }

    suspend fun toggleCompleted(id: Int, completed: Boolean) {
        var completedAt: Long? = null;
        if (completed) {
            completedAt = System.currentTimeMillis()
        }

        todoDao.setCompleted(id, completedAt)
    }

    suspend fun deleteTodo(id: Int) {
        return todoDao.deleteById(id)
    }
}