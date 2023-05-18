package com.jdccmobile.pokecombat.data.pokeApi.pokemonInfoResponse

import com.google.gson.annotations.SerializedName

data class Stat(
    @SerializedName("base_stat") val base_stat: Int,
    @SerializedName("effort") val effort: Int,
    @SerializedName("stat") val stat: StatX
)
