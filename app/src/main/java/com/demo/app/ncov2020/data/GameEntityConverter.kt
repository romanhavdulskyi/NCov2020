package com.demo.app.ncov2020.data

import com.demo.app.ncov2020.data.GameProperties.abilityMap
import com.demo.app.ncov2020.data.GameProperties.symptomMap
import com.demo.app.ncov2020.data.GameProperties.transmissionMap
import com.demo.app.ncov2020.data.room_data.GameCountry
import com.demo.app.ncov2020.data.room_data.GameState
import com.demo.app.ncov2020.logic.Callback.GameStateForEntity
import com.demo.app.ncov2020.logic.Country.*
import com.demo.app.ncov2020.logic.Country.State.*
import com.demo.app.ncov2020.logic.MainPart.GameStateReali
import java.util.*

object GameEntityConverter {

    fun applyChangesToEntity(gameStateForEntity : GameStateForEntity, gameState: GameState) : GameState
    {
        gameState.globalCure = gameStateForEntity.globalCure
        gameState.disease?.infectivity = gameStateForEntity.disease.infectivity
        gameState.disease?.lethality = gameStateForEntity.disease.lethality
        gameState.disease?.severity = gameStateForEntity.disease.severity

        gameState.disease?.transmissionsIds?.clear()
        gameState.disease?.abilitiesIds?.clear()
        gameState.disease?.symptomsIds?.clear()
        gameState.upgradePoints = gameStateForEntity.upgradePoints

        for (item in gameStateForEntity.disease.transmissions) {
            val keys = transmissionMap.filterValues { it.name == item.name }.keys
            if (keys.isNotEmpty())
                gameState.disease?.transmissionsIds?.add(keys.first())
        }

        for (item in gameStateForEntity.disease.abilities) {
            val keys = abilityMap.filterValues { it.name == item.name }.keys
            if (keys.isNotEmpty())
                gameState.disease?.abilitiesIds?.add(keys.first())
        }

        for (item in gameStateForEntity.disease.symptoms) {
            val keys = symptomMap.filterValues { it.name == item.name }.keys
            if (keys.isNotEmpty())
                gameState.disease?.symptomsIds?.add(keys.first())
        }

        gameState.countries?.clear()
        val iterator = gameStateForEntity.countryComposite.iterator;
            while(iterator.hasNext()) {
                val item = iterator.next() as Country
                val gameCountry = GameCountry(playerUUID = gameState.playerGUID, amountOfPeople = item.amountOfPeople, healthyPeople = item.healthyPeople,
                        infected = item.isInfected, infectedPeople =  item.infectedPeople, deadPeople = item.deadPeople,
                        name = item.name, countryUUID = item.countryGUID, rich = item.isRich, slowInfect = item.slowInfect, openAirport = item.isOpenAirport,
                        openGround = item.isOpenGround, openSchool = item.isOpenSchool, openSeaport = item.isOpenSeaport,
                        pathsAir = convertCountry(item.pathsAir), pathsGround = convertCountry(item.pathsGround), pathsSea = convertCountry(item.pathsSea),
                        urls = item.hronology.urls, climate = item.climate.ordinal, medicineLevel = item.medicineLevel.ordinal,
                        knowAboutVirus = item.isKnowAboutVirus, state = convertStateToInt(item.state as BaseCountryState))
                gameState.countries?.add(gameCountry)
            }
        return gameState
    }

    fun createFromGameState(gameState: GameState, countryMap : HashMap<String, Country>) : GameStateReali
    {
        val result = gameState.countries
        val savedDisease = gameState.disease
        val disease = com.demo.app.ncov2020.logic.Disease.Disease(savedDisease?.diseaseName)
        disease.lethality = savedDisease?.lethality!!
        disease.severity = savedDisease.severity!!

        if (savedDisease.abilitiesIds == null)
            savedDisease.abilitiesIds = mutableListOf()

        for (item in savedDisease.abilitiesIds!!)
            disease.abilities.add(abilityMap[item])

        if (savedDisease.symptomsIds == null)
            savedDisease.symptomsIds = mutableListOf()

        for (item in savedDisease.symptomsIds!!)
            disease.symptoms.add(symptomMap[item])

        if (savedDisease.transmissionsIds == null)
            savedDisease.transmissionsIds = mutableListOf()

        for (item in savedDisease.transmissionsIds!!)
            disease.transmissions.add(transmissionMap[item])

        val countryComposite = CountryComposite("Root")
        if (result != null) {
            for (item in result) {
                val newItem = item?.let { buildCountry(it) }
                countryMap[newItem?.name!!] = newItem
                countryComposite.addComponent(newItem)

            }
        }
        if (result != null) {
            for (item in result) {
                val country = countryMap[item?.name!!]
                item.pathsAir?.let {
                    for (item2 in it)
                        country?.addPathAir(countryMap[item2])
                }
                item.pathsGround?.let {
                    for (item2 in it)
                        country?.addPathsGround(countryMap[item2])
                }
                item.pathsSea?.let {
                    for (item2 in it)
                        country?.addPathSea(countryMap[item2])
                }
            }
        }

       return GameStateReali.init(1, "1", countryComposite, disease, gameState.globalCure, Calendar.getInstance(), gameState.upgradePoints!!)
    }

    private fun buildCountry(item : GameCountry) : Country
    {
        val newItem =  CountryBuilder()
                .setName(item.name)
                .setAmountOfPeople(item.amountOfPeople!!)
                .setOpenGround(item.openGround!!)
                .setOpenAirport(item.openAirport!!)
                .setOpenSchool(item.openSchool!!)
                .setOpenSeaport(item.openSeaport!!)
                .setRich(item.rich!!)
                .setClimate(Climate.valueOf(item.climate))
                .setMedicineLevel(MedicineLevel.valueOf(item.medicineLevel))
                .setHronology(Hronology(item.urls))
                .setCureKoef(item.cureKoef!!)
                .setInfected(item.infected!!)
                .buildCountry()

        newItem.setIsInfected( item.infected!!)
        newItem.slowInfect = item.slowInfect!!
        newItem.healthyPeople = item.healthyPeople!!
        newItem.infectedPeople = item.infectedPeople!!
        newItem.deadPeople = item.deadPeople!!
        newItem.isKnowAboutVirus = item.knowAboutVirus
        val state =  convertIntToState(item.state,newItem)
        state?.country = newItem
        newItem.state = state
        newItem.countryGUID = item.countryUUID
        return newItem
    }

    private fun convertStateToInt(countryState: BaseCountryState): Int {
        return when (countryState) {
            is CountryStateUndiscovered -> 0
            is CountryStateDoNotTakeActions -> 1
            is CountryStateCarantine -> 2
            is CountryStateCloseAllPaths -> 3
            else -> -1
        }
    }

    private fun convertCountry(countries: List<Country>): List<String> {
        return countries.map { it.name }
    }

    private fun convertIntToState(index: Int, newItem: Country): BaseCountryState? {
        return when (index) {
            0 -> CountryStateUndiscovered(newItem)
            1 -> CountryStateDoNotTakeActions(newItem)
            2 -> CountryStateCarantine(newItem)
            else -> CountryStateCloseAllPaths(newItem)
        }
    }
}
