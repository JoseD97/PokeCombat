package com.jdccmobile.pokecombat.data.api.response

data class PokedexResult(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<PokemonList>
)