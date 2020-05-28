package com.demo.app.ncov2020.game

import com.demo.app.ncov2020.data.GameEntityConverter
import com.demo.app.ncov2020.data.GameProperties.abilityMap
import com.demo.app.ncov2020.data.GameProperties.symptomMap
import com.demo.app.ncov2020.data.GameProperties.transmissionMap
import com.demo.app.ncov2020.data.GameRepositoryFacade
import com.demo.app.ncov2020.data.room_data.GameState
import com.demo.app.ncov2020.logic.Callback.Callback
import com.demo.app.ncov2020.logic.Callback.CallbackType
import com.demo.app.ncov2020.logic.Callback.GameStateForEntity
import com.demo.app.ncov2020.logic.Country.ConcreateVisitor
import com.demo.app.ncov2020.logic.Country.Country
import com.demo.app.ncov2020.logic.Disease.Ability
import com.demo.app.ncov2020.logic.Disease.Symptom
import com.demo.app.ncov2020.logic.Disease.Transmission
import com.demo.app.ncov2020.logic.MainPart.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap

class GameProviderImpl(private val gameRepositoryFacade: GameRepositoryFacade) : GameProvider {
    private lateinit var gameStateDecorator: GameStateLogDecorator
    private var guid: String? = null
    private var clients: MutableList<GameProvider.Client?> = mutableListOf()
    private val executor = Executors.newSingleThreadExecutor()
    private lateinit var scheduledExecutor: ScheduledExecutorService
    private var gameState: GameState? = null
    private var snapshotFromMakeSnapshot: GameStateForEntity? = null
    private var lastSnapshot: GameStateForEntity? = null

    private var execTask: Runnable = object : Runnable {
        override fun run() {
            gameStateDecorator.pastOneTimeUnit()
            scheduledExecutor.schedule(this, 5L, TimeUnit.SECONDS)
        }
    }

    private val countryMap = HashMap<String, Country>()


    var callback = Callback { gameStateForEntity, callbackType ->
        run {
            executor.execute {
                lastSnapshot = gameStateForEntity.clone() as GameStateForEntity
                notifyAll(Game(gameStateForEntity, callbackType))
                gameState = GameEntityConverter.applyChangesToEntity(gameStateForEntity, gameState!!)
                gameState?.let { gameRepositoryFacade.updateState(it) }
            }

        }
    }

    override fun initGame(guid: String) {
        this.guid = guid
        executor.execute {

            this.gameState = gameRepositoryFacade.getState(guid)
            if (gameState == null)
                this.gameState = gameRepositoryFacade.createState(playerGUID = guid, virusName = "TestVirus")

            gameStateDecorator = GameStateLogDecorator(GameStateCallbackDecorator(GameEntityConverter.createFromGameState(gameState!!, countryMap), callback))
            gameStateDecorator.addSymptom(symptomMap["cough"])
            gameStateDecorator.addSymptom(symptomMap["pnevmonia"])
            gameStateDecorator.addAbility(abilityMap["antibiotics"])
            gameStateDecorator.addTransmission(transmissionMap["plains"])
            gameStateDecorator.addTransmission(transmissionMap["tourist"])
            gameStateDecorator.addTransmission(transmissionMap["ship"]);
            try {
                GameStateReali.getInstance().countryComposite.accept(ConcreateVisitor())
            }catch (e:Exception){
                e.printStackTrace()
            }

            scheduledExecutor = Executors.newSingleThreadScheduledExecutor()
            scheduledExecutor.schedule(execTask, 5L, TimeUnit.SECONDS)
        }

    }


    override fun infectCountry(countryName: String) {
        executor.execute {
            gameStateDecorator.infectComponentByName(countryName)
        }
    }

    override fun addSymptom(symptom: Symptom) {
        executor.execute {
            gameStateDecorator.buyStuff(symptom)
        }
    }


    override fun addAbility(ability: Ability) {
        executor.execute {
            gameStateDecorator.buyStuff(ability)
        }
    }

    override fun addTransmission(transmission: Transmission) {
        executor.execute {
            gameStateDecorator.buyStuff(transmission)
        }
    }

    override fun addClient(client: GameProvider.Client) {
        if(!clients.contains(client)) {
            clients.add(client)
            if(lastSnapshot != null)
                client.onChanged(Game(lastSnapshot!!, CallbackType.LOADSNAPSHOT))
        }
    }

    override fun removeClient(client: GameProvider.Client) {
        clients.remove(client)
    }

    override fun notifyAll(game: Game)
    {
        for (item in clients)
        {
            item?.onChanged(game)
        }
    }

    override fun loadSnapshot() {
        if (snapshotFromMakeSnapshot == null)
            gameStateDecorator.loadSnapshot(lastSnapshot)
        else
            gameStateDecorator.loadSnapshot(snapshotFromMakeSnapshot)
    }

    override fun makeSnapshot() {
        snapshotFromMakeSnapshot = gameStateDecorator.makeSnapshot()
    }

    override fun setStrategy(strategy: Strategy) {
        executor.execute{ gameStateDecorator.setStrategy(strategy) }
    }

    override fun setCountryForStrategy(countryName: String) {
        executor.execute{
            gameStateDecorator.executeStrategy(mutableListOf(countryMap[countryName]))
        }
    }

    private object HOLDER {
        val INSTANCE = GameProviderImpl(GameRepositoryFacade.INSTANCE!!)
    }

    companion object {
        val INSTANCE: GameProvider by lazy { HOLDER.INSTANCE }
    }


}