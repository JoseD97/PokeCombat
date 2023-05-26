package com.jdccmobile.pokecombat.data.api

import com.jdccmobile.pokecombat.data.api.response.PokedexResult
import retrofit2.Response
import retrofit2.http.GET

interface PokemonApiClient {
    @GET("pokemon")
    suspend fun getAllPokemons(): Response<PokedexResult>

//    @GET("pokemon/1")
//    suspend fun getPokemonInfo(): Response<PokemonInfoResult>
}