package com.demo.app.ncov2020.game

import com.demo.app.ncov2020.data.GameRepositoryFacade
import com.demo.app.ncov2020.data.room_data.GameCountry
import com.demo.app.ncov2020.data.room_data.GameState
import com.demo.app.ncov2020.logic.Abilities.HandlerAntibiotics1
import com.demo.app.ncov2020.logic.Callback.Callback
import com.demo.app.ncov2020.logic.Country.*
import com.demo.app.ncov2020.logic.Country.State.*
import com.demo.app.ncov2020.logic.Disease.*
import com.demo.app.ncov2020.logic.MainPart.GameStateCallbackDecorator
import com.demo.app.ncov2020.logic.MainPart.GameStateLogDecorator
import com.demo.app.ncov2020.logic.MainPart.GameStateReali
import com.demo.app.ncov2020.logic.Transsmission.HandlerAIR
import com.demo.app.ncov2020.logic.Transsmission.HandlerGround
import com.demo.app.ncov2020.logic.Transsmission.HandlerWater
import com.demo.app.ncov2020.logic.cure.GlobalCure
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap

class GameProviderImpl(private val gameRepositoryFacade: GameRepositoryFacade) : GameProvider {
    private lateinit var gameStateCallbackDecorator: GameStateLogDecorator
    private var guid: String? = null
    private var client: GameProvider.Client? = null
    private val executor = Executors.newSingleThreadExecutor()
    private lateinit var scheduledExecutor: ScheduledExecutorService
    private var gameState: GameState? = null
    private val abilityMap = HashMap<String, Ability>()
    private val transmissionMap = HashMap<String, Transmission>()
    private val symptomMap = HashMap<String, Symptom>()
    private var execTask: Runnable = object : Runnable {
        override fun run() {
            gameStateCallbackDecorator.pastOneTimeUnit()
            scheduledExecutor.schedule(this, 5L, TimeUnit.SECONDS)
        }
    }

    private val countryMap = HashMap<String, Country>()


    var callback = Callback { gameStateForEntity, callbackType ->
        run {
            executor.execute {
                client?.onChanged(Game(gameStateForEntity, callbackType))
                gameState?.disease?.infectivity = gameStateForEntity.disease.infectivity
                gameState?.disease?.lethality = gameStateForEntity.disease.lethality
                gameState?.disease?.severity = gameStateForEntity.disease.severity

                gameState?.disease?.transmissionsIds?.clear()
                gameState?.disease?.abilitiesIds?.clear()
                gameState?.disease?.symptomsIds?.clear()

                for (item in gameStateForEntity.disease.transmissions) {
                    val keys = transmissionMap.filterValues { it.name == item.name }.keys
                    if (keys.isNotEmpty())
                        gameState?.disease?.transmissionsIds?.add(keys.first())
                }

                for (item in gameStateForEntity.disease.abilities) {
                    val keys = abilityMap.filterValues { it.name == item.name }.keys
                    if (keys.isNotEmpty())
                        gameState?.disease?.abilitiesIds?.add(keys.first())
                }

                for (item in gameStateForEntity.disease.symptoms) {
                    val keys = symptomMap.filterValues { it.name == item.name }.keys
                    if (keys.isNotEmpty())
                        gameState?.disease?.symptomsIds?.add(keys.first())
                }

                gameState?.countries?.clear()
                val iterator = gameStateForEntity.countryComposite.iterator
                while (iterator?.hasNext()!!){
                    val item =iterator?.next() as Country
                    val gameCountry = GameCountry(playerUUID = guid, amountOfPeople = item.amountOfPeople, healthyPeople = item.amountOfPeople,
                            name = item.name, countryUUID = item.countryGUID, rich = item.isRich, slowInfect = item.slowInfect,
                            pathsAir = convertCountry(item.pathsAir), pathsGround = convertCountry(item.pathsGround), pathsSea = convertCountry(item.pathsSea),
                            urls = item.hronology.urls, climate = item.climate.ordinal, medicineLevel = item.medicineLevel.ordinal, knowAboutVirus = item.isKnowAboutVirus, state = convertStateToInt(item.state))
                    gameState?.countries?.add(gameCountry)
                }

                gameState?.let { gameRepositoryFacade.updateState(it) }

            }

        }
    }

    private fun convertCountry(countries: List<Country>): List<String> {
        return countries.map { it.name }
    }

    override fun initGame(guid: String) {
        this.guid = guid
        executor.execute {
            symptomMap["pnevmonia"] = Symptom("Pnevmonia", "Hard to breathe", 2.0, 4, 0)
            symptomMap["cough"] = Symptom("Cough", "A-a-a-pchi", 2.0, 4, 0)
            abilityMap["antibiotics"] = Ability("Antibiotics1", "Can survive Level1 antibiotics", TypeAbility.ANTIBIOTICS1, HandlerAntibiotics1())
            transmissionMap["plains"] = Transmission("Plains transmission", "You will be able to infect by plains", TypeTrans.AIR, HandlerAIR())
            transmissionMap["tourist"] = Transmission("Tourist transmission", "You will be able to infect by tourists", TypeTrans.GROUND, HandlerGround())
            transmissionMap["ship"] = Transmission("Ship transmission", "You will be able to infect by ships", TypeTrans.WATER, HandlerWater())

            this.gameState = gameRepositoryFacade.getState(guid)
            if (gameState == null)
                this.gameState = gameRepositoryFacade.createState(playerGUID = guid, virusName = "TestVirus")
            val result = gameState?.countries
            val savedDisease = gameState?.disease
            val disease = Disease(savedDisease?.diseaseName)
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
                    val newItem = CountryBuilder()
                            .setName(item?.name)
                            .setAmountOfPeople(item?.amountOfPeople!!)
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

                        newItem.slowInfect = item.slowInfect!!
                        newItem.healthyPeople = item.healthyPeople!!
                        newItem.infectedPeople = item.infectedPeople!!
                        newItem.deadPeople = item.deadPeople!!
                        newItem.isKnowAboutVirus = item.knowAboutVirus
                        val state =  convertIntToState(item.state)
                        state?.country = newItem
                        newItem.state = state
                        countryMap[item.name!!] = newItem
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

            gameStateCallbackDecorator = GameStateLogDecorator(GameStateCallbackDecorator.init(GameStateReali.init(1, "1", countryComposite, disease, GlobalCure(1000000)), callback))
            gameStateCallbackDecorator.addSymptom(Symptom("Pnevmonia", "Hard to breathe", 2.0, 4, 0))
            gameStateCallbackDecorator.addSymptom(Symptom("Cough", "A-a-a-pchi", 2.0, 4, 0))
            gameStateCallbackDecorator.addAbility(Ability("Antibiotics1", "Can survive Level1 antibiotics", TypeAbility.ANTIBIOTICS1, HandlerAntibiotics1()))
            gameStateCallbackDecorator.addTransmission(Transmission("Plains transmission", "You will be able to infect by plains", TypeTrans.AIR, HandlerAIR()))
            gameStateCallbackDecorator.addTransmission(Transmission("Tourist transmission", "You will be able to infect by tourists", TypeTrans.GROUND, HandlerGround()))
            gameStateCallbackDecorator.addTransmission(Transmission("Ship transmission", "You will be able to infect by ships", TypeTrans.WATER, HandlerWater()))
            gameStateCallbackDecorator.infectComponentByName("Ukraine");
//            countryMap["Ukraine"]?.beginInfection()

            scheduledExecutor = Executors.newSingleThreadScheduledExecutor()
            scheduledExecutor.schedule(execTask, 5L, TimeUnit.SECONDS)
        }

    }

    private fun convertStateToInt(baseCountryState: BaseCountryState): Int {
        return when (baseCountryState) {
            is CountryStateUndiscovered -> 0
            is CountryStateDoNotTakeActions -> 1
            is CountryStateCarantine -> 2
            is CountryStateCloseAll -> 3
            else -> -1
        }
    }

    private fun convertIntToState(index: Int): BaseCountryState? {
        return when (index) {
            0 -> CountryStateUndiscovered()
            1 -> CountryStateDoNotTakeActions()
            2 -> CountryStateCarantine()
            else -> CountryStateCloseAll()
        }
    }

    override fun infectCountry(country: Country) {
        //    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addSymptom(symptom: Symptom) {
        executor.execute {
            gameStateCallbackDecorator.addSymptom(symptom)
        }
    }

    override fun addAbility(ability: Ability) {
        executor.execute {
            gameStateCallbackDecorator.addAbility(ability)
        }
    }

    override fun addTransmission(transmission: Transmission) {
        executor.execute {
            gameStateCallbackDecorator.addTransmission(transmission)
        }
    }

    override fun addClient(client: GameProvider.Client) {
        this.client = client
    }

    private object HOLDER {
        val INSTANCE = GameProviderImpl(GameRepositoryFacade.INSTANCE!!)
    }

    companion object {
        val INSTANCE: GameProvider by lazy { HOLDER.INSTANCE }
    }


}