package com.jdccmobile.pokecombat.domain

import androidx.datastore.preferences.core.Preferences
import com.jdccmobile.pokecombat.data.PokemonRepository
import javax.inject.Inject

class GetVictoriesCountUC @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(key: Preferences.Key<Int>) = pokemonRepository.getVictoriesCount(key)
}