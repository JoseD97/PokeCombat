package com.jdccmobile.pokecombat.ui.viewModel

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
import kotlin.random.Random

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

    private var victoriesCount = 0

    private var isMySuperAttackLoaded = false
    private var isRivalSuperAttackLoaded = false

    fun initViewModel(myPokemonId: Int) {
        getMyPokemonInfo(myPokemonId)
        getRivalPokemonInfo()
    }

    private fun getMyPokemonInfo(myPokemonId: Int) {
        viewModelScope.launch {
            val result: PokemonInfoResult = getPokemonInfoUC(myPokemonId)
            if (result != null) myPokemonInfo.postValue(result)
            myPokemonAttack = result.stats[1].base_stat.toFloat()
            myPokemonHP = result.stats[0].base_stat.toFloat()
        }
    }

    private fun getRivalPokemonInfo() {
        viewModelScope.launch {
            val result: PokemonInfoResult =
                getPokemonInfoUC(Random.nextInt(1, 1011))  // 1010 available pokemons in pokeapi
            if (result != null) rivalPokemonInfo.postValue(result)
            rivalPokemonAttack = result.stats[1].base_stat.toFloat()
            rivalPokemonHP = result.stats[0].base_stat.toFloat()
        }
    }

    fun getTurnResult(myMove: Int, rivalMove: Int): TurnResultModel {
        val result =
            if(myMove == 2) {
                combatTurnUC(
                    myPokemonAttack, myPokemonHP, myMove, true,
                    rivalPokemonAttack, rivalPokemonHP, rivalMove, isRivalSuperAttackLoaded
                )
            } else {
            combatTurnUC(
                myPokemonAttack, myPokemonHP, myMove, isMySuperAttackLoaded,
                rivalPokemonAttack, rivalPokemonHP, rivalMove, isRivalSuperAttackLoaded
            )
        }
        myPokemonHP = result.userHP
        rivalPokemonHP = result.iaHP
        return result
    }

    fun updateAttacksLoaded(isMyAttackLoaded: Boolean, isRivalAttackLoaded: Boolean) {
        isMySuperAttackLoaded = isMyAttackLoaded
        isRivalSuperAttackLoaded = isRivalAttackLoaded
    }

    fun updateVictoriesCount(add: Int) {
        if (add == 1) victoriesCount++
        else victoriesCount = 0
    }

    fun getVictoriesCount(): Int = victoriesCount


}