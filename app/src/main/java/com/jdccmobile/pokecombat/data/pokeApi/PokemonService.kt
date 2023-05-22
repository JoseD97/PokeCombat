package com.jdccmobile.pokecombat.data.pokeApi

import android.util.Log
import com.jdccmobile.pokecombat.data.pokeApi.pokedexResponse.PokemonList
import com.jdccmobile.pokecombat.data.pokeApi.pokemonInfoResponse.PokemonInfoResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonService @Inject constructor(
    private val apiClient: PokemonApiClient
) {

    suspend fun getAllPokemons(offset : Int): List<PokemonList> {
        return withContext(Dispatchers.IO) {
            val response = apiClient.getAllPokemons(offset)
            val body = response.body()
            Log.d("JDJD", "BODY $body")
            body?.results ?: emptyList()
        }
    }

    suspend fun getPokemonInfo(pokemonId : Int): PokemonInfoResult {
        return withContext(Dispatchers.IO) {
            val response = apiClient.getPokemonInfo(pokemonId)
            val body = response.body()
            Log.d("JDJD", "BODY $body")
            body ?: PokemonInfoResult("Error de conexion", 0, emptyList(), emptyList())
        }
    }
}