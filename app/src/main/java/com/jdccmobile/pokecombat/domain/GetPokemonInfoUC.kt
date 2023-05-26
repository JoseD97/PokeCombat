package com.jdccmobile.pokecombat.domain

import com.jdccmobile.pokecombat.data.PokemonRepository
import com.jdccmobile.pokecombat.data.pokeApi.pokemonInfoResponse.PokemonInfoResult
import javax.inject.Inject


class GetPokemonInfoUC @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(pokemonId :Int): PokemonInfoResult {
        return pokemonRepository.getPokemonInfo(pokemonId)
    }
}