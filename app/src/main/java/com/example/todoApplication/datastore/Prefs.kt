package com.example.todoApplication.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.todoApplication.ui.viewModel.TodoFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Prefs(private val context: Context) {

    private object Keys {
        val SELECTED_FILTER = stringPreferencesKey("selected_filter")
    }

    fun selectedFilter(): Flow<TodoFilter> = context.datastore.data.map { prefs ->
        val value = prefs[Keys.SELECTED_FILTER] ?: TodoFilter.ALL.name
        TodoFilter.valueOf(value)
    }

    suspend fun setFilter(value: TodoFilter) {
        context.datastore.updateData {
            it.toMutablePreferences().also { prefs ->
                prefs[Keys.SELECTED_FILTER] = value.name
            }
        }
    }
}