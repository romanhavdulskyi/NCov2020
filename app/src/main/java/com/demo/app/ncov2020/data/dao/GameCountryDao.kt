package com.demo.app.ncov2020.data.dao

import androidx.room.*
import com.demo.app.ncov2020.data.room_data.GameCountry

@Dao
interface GameCountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(gameCountries: List<GameCountry>)

    @Delete
    fun delete(gameCountry : GameCountry)

    @Query("SELECT * FROM game_countries WHERE playerUUID = :playerGUID")
    fun getAllGameCountries(playerGUID : String): List<GameCountry?>?
}