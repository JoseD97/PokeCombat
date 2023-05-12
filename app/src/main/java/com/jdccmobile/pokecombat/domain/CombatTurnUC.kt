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
            val turnUserResult = userAttacks(userAttack / 10, iaHP, iaMove)
            iaNewHP = turnUserResult.first
            iaDefeated = turnUserResult.second

        }

        if (iaMove == 1 && !iaDefeated) {
            val turnIaResult = iaAttacks(iaAttack / 10, userHP, userMove)
            userNewHP = turnIaResult.first
            userDefeated = turnIaResult.second
        }

        return TurnResultModel(userNewHP, userDefeated, iaNewHP, iaDefeated)
    }

    private fun userAttacks(userAttack: Float, iaHP: Float, iaMove: Int): Pair<Float, Boolean> {
        var iaNewHP = iaHP
        var iaNewDefeated = false
        when (iaMove) {
            0 -> {
                iaNewHP = iaHP - userAttack / 2
            }
            1 -> {
                iaNewHP = iaHP - userAttack
            }
            else -> {
                //todo super ataque
            }
        }
        if (iaNewHP < 1f) {
            iaNewHP = 0f
            iaNewDefeated = true
        }
        return Pair(iaNewHP, iaNewDefeated)
    }

    private fun iaAttacks(iaAttack: Float, userHP: Float, userMove: Int): Pair<Float, Boolean> {
        var userNewHP = userHP
        var userNewDefeated = false
        when (userMove) {
            0 -> {
                userNewHP = userHP - iaAttack / 2
            }
            1 -> {
                userNewHP = userHP - iaAttack
            }
            else -> {
                //todo super ataque
            }
        }
        if (userNewHP < 1f) {
            userNewHP = 0f
            userNewDefeated = true
        }
        return Pair(userNewHP, userNewDefeated)
    }
}