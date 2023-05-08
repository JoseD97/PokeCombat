package com.jdccmobile.pokecombat.domain

import javax.inject.Inject

class CombatTurnUC @Inject constructor() {

    operator fun invoke(
        userAttack: Float, userHP: Float, userMove: Int,
        iaAttack: Float, iaHP: Float, iaMove: Int
    ): TurnResultModel {
        var userNewHP = userHP
        var iaNewHP = iaHP
        var iaDefeated = false
        var userDefeated = false

        if (userMove == 1 && !userDefeated) {
            val turnUserResult  = userAttacks(userAttack/10, iaHP, iaMove)
            iaNewHP = turnUserResult.first
            iaDefeated = turnUserResult.second

        }
        if (iaMove == 1 && !iaDefeated) {
            val turnIaResult = iaAttacks(iaAttack/10, userHP, userMove)
            userNewHP = turnIaResult.first
            userDefeated = turnIaResult.second
        }
        return TurnResultModel(userNewHP, userDefeated, iaNewHP, iaDefeated)
    }

    private fun userAttacks(userAttack: Float, iaHP: Float, iaMove: Int): Pair<Float, Boolean> {
        var iaNewHP = iaHP
        var iaNewDefeated = false
        if (iaMove != 0) { // If ia does not dodge
            iaNewHP = iaHP - userAttack
        }
        if (iaNewHP < 0) iaNewDefeated = true
        return Pair(iaNewHP, iaNewDefeated)
    }

    private fun iaAttacks(iaAttack: Float, userHP: Float, userMove: Int): Pair<Float, Boolean> {
        var userNewHP = userHP
        var userNewDefeated = false
        if (userMove != 0) { // If user does not dodge
            userNewHP = userHP - iaAttack
        }
        if (userNewHP < 0) userNewDefeated = true
        return Pair(userNewHP, userNewDefeated)
    }
}