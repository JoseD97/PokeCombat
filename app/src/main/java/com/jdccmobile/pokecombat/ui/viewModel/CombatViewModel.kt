package com.jdccmobile.pokecombat.ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdccmobile.pokecombat.data.pokeApi.pokemonInfoResponse.PokemonInfoResult
import com.jdccmobile.pokecombat.domain.CombatTurnUC
import com.jdccmobile.pokecombat.domain.GetPokemonInfoUC
import com.jdccmobile.pokecombat.domain.TurnResultModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CombatViewModel @Inject constructor(
    private val getPokemonInfoUC: GetPokemonInfoUC,
    private val combatTurnUC: CombatTurnUC
) : ViewModel() {

    val myPokemonInfo = MutableLiveData<PokemonInfoResult>()
    val rivalPokemonInfo = MutableLiveData<PokemonInfoResult>()

    private var myPokemonAttack = 0f
    private var myPokemonHP = 0f
    private var rivalPokemonAttack = 0f
    private var rivalPokemonHP = 0f

    fun initViewModel(myPokemonId: Int){
        getMyPokemonInfo(myPokemonId)
        getRivalPokemonInfo(1)
    }

    private fun getMyPokemonInfo(myPokemonId: Int) {
        viewModelScope.launch {
            val result: PokemonInfoResult = getPokemonInfoUC(myPokemonId)
            if(result != null) myPokemonInfo.postValue(result)
            myPokemonAttack = result.stats[1].base_stat.toFloat()
            myPokemonHP = result.stats[0].base_stat.toFloat()
        }
    }

    private fun getRivalPokemonInfo(rivalPokemonId: Int) {
        viewModelScope.launch {
            val result: PokemonInfoResult = getPokemonInfoUC(rivalPokemonId)
            if(result != null) rivalPokemonInfo.postValue(result)
            rivalPokemonAttack = result.stats[1].base_stat.toFloat()
            rivalPokemonHP = result.stats[0].base_stat.toFloat()
        }
    }

    fun getTurnResult(myMove: Int, rivalMove: Int) : TurnResultModel{
        val result = combatTurnUC(myPokemonAttack, myPokemonHP, myMove, rivalPokemonAttack, rivalPokemonHP, rivalMove)
        myPokemonHP = result.userHP
        rivalPokemonHP = result.iaHP
        return result
    }


}