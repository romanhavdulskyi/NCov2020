package com.demo.app.ncov2020.data

import com.demo.app.ncov2020.data.db_data.GameState
import io.realm.Realm


class GameStateRepository {
    fun getState(userId : String) : GameState?
    {
        val realm = Realm.getDefaultInstance()
        try {
            return realm.where(GameState::class.java).equalTo("userId", userId).findFirst()
        }catch (e : Exception)
        {
            e.printStackTrace()
        } finally {
            realm.close()

        }
        return null
    }

    fun saveState(gameState: GameState)
    {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(gameState)
        realm.commitTransaction()
        realm.close()
    }

    fun createState(userId: String) : GameState
    {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val gameState = realm.createObject(GameState::class.java)
        gameState.userId = userId
        realm.copyToRealmOrUpdate(gameState)
        realm.commitTransaction()
        realm.close()
        return gameState
    }
}