package com.example.todoApplication.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.first

class Prefs(private val context: Context) {

    private object Keys {
        val SELECTED_FILTER = intPreferencesKey("selected_filter")
    }

    suspend fun setFilter(value: Int) {
        context.datastore.edit { prefs ->
            prefs[Keys.SELECTED_FILTER] = value
        }
    }

    suspend fun getFilter(): Int? {
        return context.datastore.data.first()[Keys.SELECTED_FILTER]
    }
}