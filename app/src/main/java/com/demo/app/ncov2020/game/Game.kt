package com.demo.app.ncov2020.game

import com.demo.app.ncov2020.common.MapBoxUtils
import com.demo.app.ncov2020.logic.Callback.CallbackType
import com.demo.app.ncov2020.logic.Callback.GameStateForEntity
import com.demo.app.ncov2020.logic.Country.Country
import com.demo.app.ncov2020.logic.Disease.Ability
import com.demo.app.ncov2020.logic.Disease.Disease
import com.demo.app.ncov2020.logic.Disease.Symptom
import com.demo.app.ncov2020.logic.Disease.Transmission
import com.demo.app.ncov2020.map.MapCountryData
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class Game() {
    var callbackReason : CallbackType? = null
    var infectedCountryShort: HashMap<String, MapCountryData> = HashMap()
    var infectedCountries: HashMap<String, Country> = HashMap()
    var dateTime: Date? = null
    var upgradePoints: Int? = null
    var abilities : HashSet<Ability>? = null
    var transmission : HashSet<Transmission>? = null
    var symptom : HashSet<Symptom>? = null

    constructor(gameStateForEntity: GameStateForEntity, callbackType: CallbackType) : this()
    {
        callbackReason = callbackType
        infectedCountries = gameStateForEntity.infectedCountries
        if(gameStateForEntity.infectedCountries.isNotEmpty())
        {
            for(item in gameStateForEntity.infectedCountries)
                infectedCountryShort[item.key] = (MapCountryData(item.key, MapBoxUtils.getPointsForCountry(item.key), item.value.percentOfInfAndDeadPeople))

        }
        dateTime = gameStateForEntity.date.clone() as Date
        upgradePoints = gameStateForEntity.upgradePointsCalc.upgradePoints
        abilities = gameStateForEntity.disease.abilities.toHashSet()
        transmission = gameStateForEntity.disease.transmissions.toHashSet()
        symptom = gameStateForEntity.disease.symptoms.toHashSet()
    }

    override fun toString(): String {
        return "$infectedCountryShort $dateTime $upgradePoints"
    }
}