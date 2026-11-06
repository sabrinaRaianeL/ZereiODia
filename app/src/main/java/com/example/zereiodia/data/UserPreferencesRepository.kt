package com.example.zereiodia.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferencesRepository (private val dataStore: DataStore<Preferences>) {

    companion object {
        val SORT_ORDER_KEY = stringPreferencesKey("sort_order")
    }

    val sortOrderFlow: Flow<String> = dataStore.data.map {
        preferences -> preferences[SORT_ORDER_KEY] ?: "ID_DESC"
    }

    suspend fun saveSortOrder(sortOrder: String) {
        dataStore.edit {
            preferences -> preferences[SORT_ORDER_KEY] = sortOrder
        }
    }
}