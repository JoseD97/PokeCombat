package com.jdccmobile.pokecombat.data

import androidx.datastore.preferences.core.Preferences
import com.jdccmobile.pokecombat.data.api.response.PokemonList
import com.jdccmobile.pokecombat.data.pokeApi.PokemonService
import com.jdccmobile.pokecombat.data.pokeApi.pokemonInfoResponse.PokemonInfoResult
import com.jdccmobile.pokecombat.data.preferences.PreferencesDataStore
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonService: PokemonService,
    private val preferences: PreferencesDataStore
) {

    // PokeApi
    suspend fun getAllPokemons(offset : Int): List<PokemonList> {
        return pokemonService.getAllPokemons(offset)
    }

    suspend fun getPokemonInfo(pokemonId : Int): PokemonInfoResult {
        return pokemonService.getPokemonInfo(pokemonId)
    }

    // DataStore
    suspend fun putVictoriesCount(key: Preferences.Key<Int>, value: Int){
        preferences.putInt(key, value)
    }

    suspend fun getVictoriesCount(key: Preferences.Key<Int>) : Int? {
        return preferences.getInt(key)
    }

    suspend fun putIsInfoCombatShowed(key: Preferences.Key<Boolean>, value: Boolean){
        preferences.putBoolean(key, value)
    }

    suspend fun getIsInfoCombatShowed(key: Preferences.Key<Boolean>) : Boolean? {
        return preferences.getBoolean(key)
    }




}