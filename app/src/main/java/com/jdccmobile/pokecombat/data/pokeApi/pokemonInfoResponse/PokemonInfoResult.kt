package com.jdccmobile.pokecombat.data.pokeApi.pokemonInfoResponse

import com.google.gson.annotations.SerializedName

data class PokemonInfoResult(
    @SerializedName("name") val name: String,
    @SerializedName("id") val id: Int,
    @SerializedName("stats") val stats: List<Stat>,
    @SerializedName("types") val types: List<Type>
)
