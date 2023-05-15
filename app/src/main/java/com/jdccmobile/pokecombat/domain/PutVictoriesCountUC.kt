package com.jdccmobile.pokecombat.domain

import com.jdccmobile.pokecombat.data.PokemonRepository
import javax.inject.Inject

class PutVictoriesCountUC @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(key: String, value: Int){
        pokemonRepository.putVictoriesCount(key, value)
    }

}