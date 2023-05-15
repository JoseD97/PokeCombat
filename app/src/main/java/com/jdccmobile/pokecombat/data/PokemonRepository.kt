package com.jdccmobile.pokecombat.data

import com.jdccmobile.pokecombat.data.pokeApi.PokemonService
import com.jdccmobile.pokecombat.data.pokeApi.pokedexResponse.PokemonList
import com.jdccmobile.pokecombat.data.pokeApi.pokemonInfoResponse.PokemonInfoResult
import com.jdccmobile.pokecombat.data.preferences.Preferences
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonService: PokemonService,
    private val preferences: Preferences // todo probar con el imp
) {

    // PokeApi
    suspend fun getAllPokemons(): List<PokemonList> {
        return pokemonService.getAllPokemons()
    }

    suspend fun getPokemonInfo(pokemonId : Int): PokemonInfoResult {
        return pokemonService.getPokemonInfo(pokemonId)
    }

    // DataStore
    suspend fun putVictoriesCount(key: String, value: Int){
        preferences.putVictoriesCount(key, value)
    }

    suspend fun getVictoriesCount(key: String) : Int? {
        return preferences.getVictoriesCount(key)
    }


}