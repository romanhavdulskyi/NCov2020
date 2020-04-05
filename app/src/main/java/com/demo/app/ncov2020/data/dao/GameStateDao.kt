package com.demo.app.ncov2020.data.dao

import androidx.room.*
import com.demo.app.ncov2020.data.room_data.GameCountry
import com.demo.app.ncov2020.data.room_data.GameState

@Dao
interface GameStateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(gameState: GameState)

    @Delete
    fun delete(gameState: GameState)

    @Query("SELECT * FROM game_states WHERE playerGUID = :playerGUID")
    fun getGameState(playerGUID : String): GameState?
}