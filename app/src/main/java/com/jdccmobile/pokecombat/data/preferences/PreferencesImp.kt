package com.jdccmobile.pokecombat.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import java.lang.Exception
import javax.inject.Inject

private const val PREFERENCES_NAME = "preferences"
const val VICTORIES = "victories"

private val Context.dataStore by preferencesDataStore(name = PREFERENCES_NAME)

class PreferencesImp @Inject constructor(
    private val context: Context
) : Preferences {

    override suspend fun putVictoriesCount(key: String, value: Int){
        val preferenceKey = intPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferenceKey] = value
        }
    }

    override suspend fun getVictoriesCount(key: String) : Int? {
        return try {
            val preferenceKey = intPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            preferences[preferenceKey]
        } catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

}