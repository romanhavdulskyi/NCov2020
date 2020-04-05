package com.demo.app.ncov2020.data

import com.demo.app.ncov2020.data.dao.CommonCountryDao
import com.demo.app.ncov2020.data.dao.DiseaseDao
import com.demo.app.ncov2020.data.dao.GameCountryDao
import com.demo.app.ncov2020.data.dao.GameStateDao
import com.demo.app.ncov2020.data.room_data.CommonCountry
import com.demo.app.ncov2020.data.room_data.Disease
import com.demo.app.ncov2020.data.room_data.GameCountry
import com.demo.app.ncov2020.data.room_data.GameState


class GameStateRepository(var commonCountryDao: CommonCountryDao, var countryDao: GameCountryDao, var gameStateDao: GameStateDao, var diseaseDao: DiseaseDao) {
    fun getState(playerGUID : String) : GameState?
    {
        val gameState =  gameStateDao.getGameState(playerGUID)
        gameState?.countries = countryDao.getAllGameCountries(playerGUID)
        gameState?.disease = diseaseDao.getDisease(playerGUID)
        gameState?.initialize()
        return gameState
    }

    fun saveState(gameState: GameState)
    {
        gameStateDao.insert(gameState)
    }

    fun createState(playerGUID : String, virusName : String) : GameState
    {
        val disease = Disease(diseaseName = virusName, playerGUID = playerGUID, transmissionsIds = null, abilitiesIds = null, symptomsIds = null)
        val gameState =  GameState(playerGUID = playerGUID, countries = countryConverter(commonCountryDao.getAll(), playerGUID), disease = disease)
        gameState.countries = countryDao.getAllGameCountries(playerGUID)
        gameState.disease = diseaseDao.getDisease(playerGUID)
        gameState.initialize()
        return gameState
    }

    private fun countryConverter(commonCountry: List<CommonCountry>, playerGUID: String) : List<GameCountry>
    {
        val converter = ListConverter()
        val gameCountriesList = mutableListOf<GameCountry>()
        for(item in commonCountry)
        {
            val gameCountry = GameCountry(playerUUID = playerGUID ,amountOfPeople = item.amountOfPeople, healthyPeople = item.amountOfPeople,
                    name = item.name, countryUUID =  item.countryUUID, rich = item.rich, pathsAir = converter.toStringList(item.pathsAir),
                    pathsGround = converter.toStringList(item.pathsGround), pathsSea = converter.toStringList(item.pathsSea))
            gameCountriesList.add(gameCountry)
        }
        return gameCountriesList
    }

    private object HOLDER {
        val INSTANCE = GameStateRepository(AssetsAppDatabase.getInstance()!!.CommonCountryDao(), AppDatabase.getInstance()!!.GameCountryDao(), AppDatabase.getInstance()!!.GameStateDao(), AppDatabase.getInstance()!!.DiseaseDao())
    }

    companion object {
        val instance: GameStateRepository by lazy { HOLDER.INSTANCE }
    }
}