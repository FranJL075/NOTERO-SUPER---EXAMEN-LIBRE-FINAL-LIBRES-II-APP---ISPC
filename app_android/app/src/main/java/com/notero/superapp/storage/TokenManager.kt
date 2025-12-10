package com.notero.superapp.storage

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferenceKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TokenManager(private val context: Context) {
    companion object {
        private val Context.dataStore by preferencesDataStore(name = "auth_prefs")
        private val ACCESS_KEY = stringPreferencesKey("access_token")
        private val REFRESH_KEY = stringPreferencesKey("refresh_token")
    }

    suspend fun saveTokens(access: String, refresh: String = "") {
        context.dataStore.edit { prefs ->
            prefs[ACCESS_KEY] = access
            prefs[REFRESH_KEY] = refresh
        }
    }

    val accessToken: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[ACCESS_KEY] ?: ""
    }

    val refreshToken: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[REFRESH_KEY] ?: ""
    }

    suspend fun clear() {
        context.dataStore.edit(Preferences::clear)
    }
}
