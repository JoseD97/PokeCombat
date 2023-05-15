package com.jdccmobile.pokecombat.data

import com.jdccmobile.pokecombat.data.api.PokemonService
import com.jdccmobile.pokecombat.data.api.response.PokemonList
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonService: PokemonService
) {

    suspend fun getAllPokemons(): List<PokemonList> {
        return pokemonService.getAllPokemons()
    }

//    suspend fun getPokemonInfo(): PokemonInfoResponse {
//        return pokemonService.getPokemonInfo()
//    }
}