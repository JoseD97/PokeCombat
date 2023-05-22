package com.jdccmobile.pokecombat.domain

import android.util.Log
import com.jdccmobile.pokecombat.data.pokeApi.pokedexResponse.PokemonList
import com.jdccmobile.pokecombat.data.PokemonRepository
import javax.inject.Inject

class GetAllPokemonsUC @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(offset : Int): List<PokemonList> {
        Log.w("JDJD", "entro UC")
        return pokemonRepository.getAllPokemons(offset)
    }

}