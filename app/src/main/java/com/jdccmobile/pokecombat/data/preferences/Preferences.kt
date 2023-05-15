package com.jdccmobile.pokecombat.data.preferences

interface Preferences {

    suspend fun putVictoriesCount(key: String, value: Int){}

    suspend fun getVictoriesCount(key: String) : Int?
}