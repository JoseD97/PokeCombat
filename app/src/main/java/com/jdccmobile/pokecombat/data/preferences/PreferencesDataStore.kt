package com.jdccmobile.pokecombat.data.preferences

import androidx.datastore.preferences.core.Preferences

interface PreferencesDataStore {

    suspend fun putInt(key: Preferences.Key<Int>, value: Int)
    suspend fun getInt(key: Preferences.Key<Int>) : Int?

    suspend fun putBoolean(key: Preferences.Key<Boolean>, value: Boolean)
    suspend fun getBoolean(key: Preferences.Key<Boolean>) : Boolean?
}