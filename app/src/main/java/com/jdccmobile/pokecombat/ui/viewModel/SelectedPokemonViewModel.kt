package com.jdccmobile.pokecombat.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdccmobile.pokecombat.data.pokeApi.pokemonInfoResponse.PokemonInfoResult
import com.jdccmobile.pokecombat.domain.GetPokemonInfoUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectedPokemonViewModel @Inject constructor(
    private val getPokemonInfoUC: GetPokemonInfoUC
): ViewModel() {

    val pokemonInfo = MutableLiveData<PokemonInfoResult>()


    fun getPokemonInfo(pokemonId : Int){
        viewModelScope.launch {
            val result: PokemonInfoResult = getPokemonInfoUC(pokemonId)
            pokemonInfo.postValue(result)
        }
    }



}