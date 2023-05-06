package com.jdccmobile.pokecombat.data

import com.jdccmobile.pokecombat.data.pokeApi.PokemonService
import com.jdccmobile.pokecombat.data.pokeApi.pokedexResponse.PokemonList
import com.jdccmobile.pokecombat.data.pokeApi.pokemonInfoResponse.PokemonInfoResult
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonService: PokemonService
) {

    suspend fun getAllPokemons(): List<PokemonList> {
        return pokemonService.getAllPokemons()
    }

    suspend fun getPokemonInfo(pokemonId : Int): PokemonInfoResult {
        return pokemonService.getPokemonInfo(pokemonId)
    }
}