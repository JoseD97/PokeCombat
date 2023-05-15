package com.jdccmobile.pokecombat.ui.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdccmobile.pokecombat.data.api.response.PokemonList
import com.jdccmobile.pokecombat.domain.GetAllPokemonsUC

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokedexViewModel @Inject constructor(
    private val getAllPokemonsUC: GetAllPokemonsUC
) : ViewModel(){

    val pokemonsList = MutableLiveData<List<PokemonList>>()

    fun initViewModel() {
        getAllPokemons()
    }

    private fun getAllPokemons(){
        viewModelScope.launch {
            val result: List<PokemonList> = getAllPokemonsUC()
            Log.i("JDJD", "result vw $result")
            if(!result.isNullOrEmpty()) pokemonsList.postValue(result)
        }
    }
}