package com.jdccmobile.pokecombat.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import java.lang.Exception
import javax.inject.Inject

private const val PREFERENCES_NAME = "preferences"

private val Context.dataStore by preferencesDataStore(name = PREFERENCES_NAME)

 class PreferencesImp @Inject constructor(
    private val context: Context
) : PreferencesDataStore {

    override suspend fun putInt(key:  Preferences.Key<Int>, value: Int){
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    override suspend fun getInt(key: Preferences.Key<Int>) : Int? {
        return try {
            val preferences = context.dataStore.data.first()
            preferences[key]
        } catch (e: Exception){
            e.printStackTrace()
            null
        }
    }


     override suspend fun putBoolean(key: Preferences.Key<Boolean>, value: Boolean) {
         context.dataStore.edit { preferences ->
             preferences[key] = value
         }
     }

     override suspend fun getBoolean(key: Preferences.Key<Boolean>): Boolean? {
         return try {
             val preferences = context.dataStore.data.first()
             preferences[key]
         } catch (e: Exception){
             e.printStackTrace()
             null
         }
     }



 }