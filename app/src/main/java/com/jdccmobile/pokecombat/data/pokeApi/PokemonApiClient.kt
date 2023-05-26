package com.jdccmobile.pokecombat.data.pokeApi

import com.jdccmobile.pokecombat.data.api.response.PokedexResult
import com.jdccmobile.pokecombat.data.pokeApi.pokemonInfoResponse.PokemonInfoResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiClient {
    @GET("pokemon")
    suspend fun getAllPokemons(@Query("offset") offset : Int): Response<PokedexResult>

    @GET("pokemon/{pokemonId}")
    suspend fun getPokemonInfo(@Path("pokemonId") pokemonId : Int): Response<PokemonInfoResult>
}