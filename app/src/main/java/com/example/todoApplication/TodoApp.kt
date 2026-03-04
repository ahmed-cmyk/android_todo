package com.example.todoApplication

import android.app.Application
import com.example.todoApplication.data.AppDatabase
import com.example.todoApplication.data.repository.TodoRepository
import com.example.todoApplication.datastore.Prefs

class TodoApp: Application() {
    val database by lazy { AppDatabase.getInstance(this) }
    val prefs by lazy { Prefs(this) }
    val repository by lazy { TodoRepository(database.todoDao()) }
}