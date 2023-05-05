package com.jdccmobile.pokecombat.data.api

import android.util.Log
import com.jdccmobile.pokecombat.data.api.response.PokemonList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonService @Inject constructor(
    private val apiClient: PokemonApiClient
) {

    suspend fun getAllPokemons(): List<PokemonList> {
        return withContext(Dispatchers.IO) { // devuelve la informacion cuando este creado sin afectar la hilo principal
            val response = apiClient.getAllPokemons()
            val body = response.body()
            Log.d("JDJD", " BODY $body")
            body?.results ?: emptyList()
        }
    }

//    suspend fun getPokemonInfo(): PokemonInfoResponse {
//        return withContext(Dispatchers.IO) {
//            val response = apiClient.getPokemonInfo()
//            val body = response.body()
//            Log.d("JDJD", " BODY $body")
//            PokemonInfoResponse(
//                body?.name ?: "Error de conexion",
//                body?.stats ?: emptyList()
//            )
//
//        }
//    }
}