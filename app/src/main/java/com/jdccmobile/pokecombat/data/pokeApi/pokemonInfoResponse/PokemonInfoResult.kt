package com.jdccmobile.pokecombat.data.pokeApi.pokemonInfoResponse

//todo poner serialized
data class PokemonInfoResult(
    val name: String,
    val id: Int,
    val stats: List<Stat>,
    val types: List<Type>
)
