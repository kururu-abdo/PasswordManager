package com.kururu.password_manager.Utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreUserData(private val context: Context) {
    // to make sure there's only one instance
    companion object {

        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

        val USER_PASSWORD_KEY = stringPreferencesKey(Constants.PASSWORD_KEY)
        val IS_FIRST_KEY = stringPreferencesKey(Constants.IS_FISRST_TIME_KEY)

    }

    //get the saved email
    val getPassword: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_PASSWORD_KEY] ?: ""
        }
    val getIsFirstTime: Flow<Boolean?> = context.dataStore.data
        .map { preferences ->
            (preferences[IS_FIRST_KEY] ?: "") == "first"
        }
    //save email into datastore
    suspend fun savePassword(password: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_PASSWORD_KEY] = password
            preferences[IS_FIRST_KEY] = "false"
        }
    }

    suspend fun saveIsFirstTime(data: Boolean) {
        context.dataStore.edit { preferences ->

            preferences[IS_FIRST_KEY] = "$data".toString()
        }
    }
}