package com.demo.app.ncov2020.data

import com.demo.app.ncov2020.data.room_data.Disease
import com.demo.app.ncov2020.data.room_data.GameState

interface GameStateRepository {
    fun getState(playerGUID : String) : GameState?


    fun saveState(gameState: GameState)


    fun updateState(gameState: GameState)


    fun createState(playerGUID : String, virusName : String) : GameState


}