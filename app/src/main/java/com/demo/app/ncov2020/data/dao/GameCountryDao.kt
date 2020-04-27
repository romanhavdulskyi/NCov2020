package com.demo.app.ncov2020.data.dao

import androidx.room.*
import com.demo.app.ncov2020.data.room_data.GameCountry

@Dao
interface GameCountryDao {

    @Update
    fun update(gameCountries: GameCountry)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(gameCountries: GameCountry)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(gameCountries: MutableList<GameCountry?>?)

    @Delete
    fun delete(gameCountry : GameCountry)

    @Query("SELECT * FROM game_countries WHERE playerUUID = :playerGUID")
    fun getAllGameCountries(playerGUID : String): List<GameCountry?>?
}