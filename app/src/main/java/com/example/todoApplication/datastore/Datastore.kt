package com.example.todoApplication.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first


class Prefs(val context: Context) {
    val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "misc")

    suspend fun saveInt(key: String, value: Int) {
        val prefKey = intPreferencesKey(key)
        context.datastore.edit { prefs ->
            prefs[prefKey] = value
        }
    }

    suspend fun getInt(key: String): Int? {
        val prefKey = intPreferencesKey(key)
        return context.datastore.data.first()[prefKey]
    }
}