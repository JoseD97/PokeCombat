package com.jdccmobile.pokecombat.domain

data class TurnResultModel(
    val userHP : Float,
    val userIsDefeated : Boolean,
    val iaHP : Float,
    val iaIsDefeated : Boolean,
)
