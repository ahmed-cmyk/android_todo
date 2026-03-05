package com.example.todoApplication.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class Prefs(private val context: Context) {

    private object Keys {
        val SELECTED_FILTER = intPreferencesKey("selected_filter")
    }

    fun selectedFilter(): Flow<Int> = context.datastore.data.map { prefs ->
        prefs[Keys.SELECTED_FILTER] ?: 0
    }

    suspend fun setFilter(value: Int) {
        context.datastore.updateData {
            it.toMutablePreferences().also { prefs ->
                prefs[Keys.SELECTED_FILTER] = value
            }
        }
    }
}