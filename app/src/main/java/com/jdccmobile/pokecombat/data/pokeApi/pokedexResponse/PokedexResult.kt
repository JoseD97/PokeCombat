package com.jdccmobile.pokecombat.data.pokeApi.pokedexResponse

import com.google.gson.annotations.SerializedName

data class PokedexResult(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String,
    @SerializedName("previous") val previous: Any,
    @SerializedName("results") val results: List<PokemonList>
)