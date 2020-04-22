package com.demo.app.ncov2020.data

import com.demo.app.ncov2020.data.dao.CommonCountryDao
import com.demo.app.ncov2020.data.dao.DiseaseDao
import com.demo.app.ncov2020.data.dao.GameCountryDao
import com.demo.app.ncov2020.data.dao.GameStateDao
import com.demo.app.ncov2020.data.room_data.CommonCountry
import com.demo.app.ncov2020.data.room_data.Disease
import com.demo.app.ncov2020.data.room_data.GameCountry
import com.demo.app.ncov2020.data.room_data.GameState


class GameStateRepo private constructor(private var commonCountryDao: CommonCountryDao, private var countryDao: GameCountryDao, private var gameStateDao: GameStateDao, private var diseaseDao: DiseaseDao) : GameStateRepository {
    override fun getState(playerGUID : String) : GameState?
    {
        val gameState =  gameStateDao.getGameState(playerGUID)
        gameState?.countries = countryDao.getAllGameCountries(playerGUID)
        gameState?.disease = diseaseDao.getDisease(playerGUID)
        return gameState
    }

    override fun saveState(gameState: GameState)
    {
        gameStateDao.insert(gameState)
    }

    override fun updateState(gameState: GameState)
    {
        gameStateDao.insert(gameState)
    }

    override fun createState(playerGUID : String, virusName : String) : GameState
    {
        val disease = Disease(diseaseName = virusName, playerGUID = playerGUID, transmissionsIds = null, abilitiesIds = null, symptomsIds = null)
        val gameState =  GameState(playerGUID = playerGUID, countries = convertCountry(commonCountryDao.getAll(), playerGUID), disease = disease)
        gameState.countries = countryDao.getAllGameCountries(playerGUID)
        gameState.disease = diseaseDao.getDisease(playerGUID)
        return gameState
    }

    private fun convertCountry(commonCountry: List<CommonCountry>, playerGUID: String) : List<GameCountry>
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


    companion object {
        var INSTANCE : GameStateRepo =  GameStateRepo(AssetsAppDatabase.getInstance()!!.CommonCountryDao(),
                AppDatabase.getInstance()!!.GameCountryDao(),
                AppDatabase.getInstance()!!.GameStateDao(),
                AppDatabase.getInstance()!!.DiseaseDao())
    }
}