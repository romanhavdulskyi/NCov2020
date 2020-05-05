package com.demo.app.ncov2020.data

import android.annotation.SuppressLint
import com.demo.app.NConApp.Companion.context
import com.demo.app.basics.concurrency.TaskCallback
import com.demo.app.ncov2020.common.CSVConverter
import com.demo.app.ncov2020.data.dao.DiseaseDao
import com.demo.app.ncov2020.data.dao.GameCountryDao
import com.demo.app.ncov2020.data.dao.GameStateDao
import com.demo.app.ncov2020.data.room_data.CommonCountry
import com.demo.app.ncov2020.data.room_data.Disease
import com.demo.app.ncov2020.data.room_data.GameCountry
import com.demo.app.ncov2020.data.room_data.GameState
import com.demo.app.ncov2020.logic.Country.Climate
import com.demo.app.ncov2020.logic.Country.CountryBuilder
import com.demo.app.ncov2020.logic.Country.Hronology
import com.demo.app.ncov2020.logic.Country.MedicineLevel
import com.demo.app.ncov2020.logic.MainPart.UpgradePointsCalc
import com.demo.app.ncov2020.logic.cure.GlobalCure
import java.io.IOException
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.collections.HashMap


class GameStateRepo private constructor(private var countryDao: GameCountryDao, private var gameStateDao: GameStateDao, private var diseaseDao: DiseaseDao) : GameStateRepository {
    override fun getState(playerGUID: String): GameState? {
        val gameState = gameStateDao.getGameState(playerGUID)
        gameState?.countries = countryDao.getAllGameCountries(playerGUID)?.toMutableList()
        gameState?.disease = diseaseDao.getDisease(playerGUID)
        return gameState
    }

    override fun saveState(gameState: GameState) {
        gameStateDao.insert(gameState)
        for (item in gameState.countries!!)
            item?.let { countryDao.insert(it) }
        diseaseDao.insert(gameState.disease!!)
    }

    override fun updateState(gameState: GameState) {
        gameStateDao.update(gameState)
        for (item in gameState.countries!!)
            item?.let { countryDao.update(it) }
        diseaseDao.update(gameState.disease!!)
    }

    override fun createState(playerGUID: String, virusName: String): GameState {
        val commonCountries: MutableList<CommonCountry> = fetchCommonCountries()
        val disease = Disease(diseaseName = virusName, playerGUID = playerGUID, transmissionsIds = null, abilitiesIds = null, symptomsIds = null)
        val upgradePointsCalc = UpgradePointsCalc();
        val gameState = GameState(playerGUID = playerGUID, countries = convertCountry(commonCountries, playerGUID), disease = disease, globalCure = GlobalCure(1000000), upgradePointsCalc = upgradePointsCalc )
        countryDao.insertAll(gameState.countries)
        diseaseDao.insert(disease)
        return gameState
    }

    private fun convertCountry(commonCountry: List<CommonCountry>, playerGUID: String): MutableList<GameCountry?>? {
        val gameCountriesList = HashMap<String, GameCountry>()
        for (item in commonCountry) {
            val gameCountry = GameCountry(playerUUID = playerGUID, amountOfPeople = item.amountOfPeople, healthyPeople = item.amountOfPeople,
                    name = item.name, countryUUID = UUID.nameUUIDFromBytes((item.countryUUID + "-" + playerGUID).toByteArray()).toString(), rich = item.rich, pathsAir = item.pathsAir,
                    pathsGround = item.pathsGround, pathsSea = item.pathsSea, urls = item.urls, climate = item.climate,
                    medicineLevel = item.medicineLevel, state = 0, knowAboutVirus = false, cureKoef = 0.0)
            gameCountriesList[item.name!!] = gameCountry
        }
        return gameCountriesList.values.toMutableList()
    }

    private fun fetchCommonCountries() :  MutableList<CommonCountry>
    {
        val listConverter = CSVConverter()
        val commonCountries = mutableListOf<CommonCountry>()
        val dataBaseHelper = DataBaseHelper(context)
        try {
            dataBaseHelper.createDatabase()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val sqLiteDatabase = dataBaseHelper.writableDatabase
        @SuppressLint("Recycle") val cur = sqLiteDatabase.rawQuery("select * from CommonCountry", null)
        if (cur.moveToFirst()) {
            do {
                val commonCountry = CommonCountry(cur.getInt(0),
                        cur.getString(1),
                        cur.getString(2),
                        cur.getLong(3),
                        cur.getInt(4) > 0,
                        listConverter.toStringList(cur.getString(5)),
                        listConverter.toStringList(cur.getString(6)),
                        listConverter.toStringList(cur.getString(7)),
                        listConverter.toStringList(cur.getString(8)),
                        cur.getInt(9),
                        cur.getInt(10))
                commonCountries.add(commonCountry)
            } while (cur.moveToNext())
        }
        return commonCountries
    }


    companion object {
        var INSTANCE: GameStateRepo = GameStateRepo(
                AppDatabase.getInstance()!!.GameCountryDao(),
                AppDatabase.getInstance()!!.GameStateDao(),
                AppDatabase.getInstance()!!.DiseaseDao())
    }
}