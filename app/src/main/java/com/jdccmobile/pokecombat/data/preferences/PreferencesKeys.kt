package com.jdccmobile.pokecombat.data.preferences

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey

object PreferencesKeys {
    val VICTORIES = intPreferencesKey("victories")
    val SHOW_COMBAT_INFO = booleanPreferencesKey("show_combat_info")
}