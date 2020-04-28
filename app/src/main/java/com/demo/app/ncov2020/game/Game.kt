package com.demo.app.ncov2020.game

import com.demo.app.ncov2020.logic.Callback.CallbackType
import com.demo.app.ncov2020.logic.Callback.GameStateForEntity
import java.util.*

class Game() {
    var hardLevelInfectedCountry: MutableList<String> = mutableListOf()
    var mediumLevelInfectedCountry: MutableList<String> = mutableListOf()
    var lowLevelInfectedCountry: MutableList<String> = mutableListOf()
    var dateTime: Date? = null

    constructor(gameStateForEntity: GameStateForEntity,  callbackType : CallbackType) : this()
    {
        hardLevelInfectedCountry.addAll(gameStateForEntity.countryComposite.hardLevelInfectedCountry)
        mediumLevelInfectedCountry.addAll(gameStateForEntity.countryComposite.mediumLevelInfectedCountry)
        lowLevelInfectedCountry.addAll(gameStateForEntity.countryComposite.lowLevelInfectedCountry)
        dateTime = gameStateForEntity.date.clone() as Date
    }

    override fun toString(): String {
        return hardLevelInfectedCountry.toString() + " " + mediumLevelInfectedCountry.toString() +
        " " + lowLevelInfectedCountry.toString() + " " + dateTime
    }
}