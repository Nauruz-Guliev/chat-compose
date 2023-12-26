package ru.kpfu.itis.core_data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val USER_ID_KEY = "USER_ID_KEY"

class UserStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    private val idKey = stringPreferencesKey(USER_ID_KEY)

    suspend fun saveUserId(id: String?) {
        id?.let {
            dataStore.edit { preferences ->
                preferences[idKey] = it
            }
        }
    }

    suspend fun getUserId(): String? {
        return dataStore.data.map { preferences ->
            preferences[idKey]
        }.first()
    }

    suspend fun clearId() {
        dataStore.edit { preferences ->
            preferences.remove(idKey)
        }
    }
}
