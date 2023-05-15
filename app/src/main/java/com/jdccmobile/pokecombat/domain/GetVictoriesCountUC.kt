package com.jdccmobile.pokecombat.domain

import com.jdccmobile.pokecombat.data.PokemonRepository
import javax.inject.Inject

class GetVictoriesCountUC @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(key: String) = pokemonRepository.getVictoriesCount(key)
}