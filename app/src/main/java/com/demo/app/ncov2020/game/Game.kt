package com.demo.app.ncov2020.game

import com.demo.app.ncov2020.logic.Callback.CallbackType
import com.demo.app.ncov2020.logic.Callback.GameStateForEntity

class Game() {
    var hardLevelInfectedCountry: MutableList<String> = mutableListOf()
    var mediumLevelInfectedCountry: MutableList<String> = mutableListOf()
    var lowLevelInfectedCountry: MutableList<String> = mutableListOf()

    constructor(gameStateForEntity: GameStateForEntity,  callbackType : CallbackType) : this()
    {
        hardLevelInfectedCountry.addAll(gameStateForEntity.countryComposite.hardLevelInfectedCountry)
        mediumLevelInfectedCountry.addAll(gameStateForEntity.countryComposite.mediumLevelInfectedCountry)
        lowLevelInfectedCountry.addAll(gameStateForEntity.countryComposite.lowLevelInfectedCountry)
    }

    override fun toString(): String {
        return hardLevelInfectedCountry.toString() + " " + mediumLevelInfectedCountry.toString() +
        " " + lowLevelInfectedCountry.toString()
    }
}