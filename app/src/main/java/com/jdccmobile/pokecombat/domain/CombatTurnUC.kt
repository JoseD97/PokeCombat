package com.jdccmobile.pokecombat.domain

import javax.inject.Inject

class CombatTurnUC @Inject constructor() {

    operator fun invoke(
        userAttack: Float, userHP: Float, userMove: Int, isMyAttackLoaded: Boolean,
        iaAttack: Float, iaHP: Float, iaMove: Int, isRivalAttackLoaded: Boolean
    ): TurnResultModel {
        var userNewHP = userHP
        var iaNewHP = iaHP
        var isNewMyAttackLoaded = isMyAttackLoaded
        var isNewRivalAttackLoaded = isRivalAttackLoaded
        var iaDefeated = false
        var userDefeated = false

        if (userMove == 1) {
            val turnUserResult : Pair<Float, Boolean>
            if(isMyAttackLoaded){
                turnUserResult = userAttacks(userAttack / 5, iaHP, iaMove)
                isNewMyAttackLoaded = false
            } else {
                turnUserResult = userAttacks(userAttack / 10, iaHP, iaMove)
            }
            iaNewHP = turnUserResult.first
            iaDefeated = turnUserResult.second
        }
        else if(userMove == 2) { isNewMyAttackLoaded = true }

        if (iaMove == 1 && !iaDefeated) {
            val turnIaResult : Pair<Float, Boolean>
            if(isRivalAttackLoaded){
                turnIaResult = iaAttacks(iaAttack / 5, userHP, userMove)
                isNewRivalAttackLoaded = false
            } else {
                turnIaResult = iaAttacks(iaAttack / 10, userHP, userMove)
            }
            userNewHP = turnIaResult.first
            userDefeated = turnIaResult.second
        }
        else if(iaMove == 2 && !iaDefeated) { isNewRivalAttackLoaded = true }
        return TurnResultModel(userNewHP, userDefeated, isNewMyAttackLoaded, iaNewHP, iaDefeated, isNewRivalAttackLoaded)
    }

    private fun userAttacks(userAttack: Float, iaHP: Float, iaMove: Int): Pair<Float, Boolean> {
        var iaNewHP: Float
        var iaNewDefeated = false
        iaNewHP = when (iaMove) {
            0 -> iaHP - userAttack / 2
            else -> iaHP - userAttack
        }
        if (iaNewHP < 1f) {
            iaNewHP = 0f
            iaNewDefeated = true
        }
        return Pair(iaNewHP, iaNewDefeated)
    }

    private fun iaAttacks(iaAttack: Float, userHP: Float, userMove: Int): Pair<Float, Boolean> {
        var userNewHP: Float
        var userNewDefeated = false
        userNewHP = when (userMove) {
            0 -> userHP - iaAttack /  2
            else -> userHP - iaAttack
        }
        if (userNewHP < 1f) {
            userNewHP = 0f
            userNewDefeated = true
        }
        return Pair(userNewHP, userNewDefeated)
    }
}