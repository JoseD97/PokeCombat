package com.jdccmobile.pokecombat.data.pokeApi.pokedexResponse

import com.google.gson.annotations.SerializedName

data class PokemonList(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)