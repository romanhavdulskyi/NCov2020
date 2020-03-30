package com.demo.app.ncov2020.data

import com.demo.app.ncov2020.data.state.GameState
import io.realm.Realm

class GameStateRepository {
    fun getState(userId : Long) : GameState?
    {
        val realm = Realm.getDefaultInstance()
        return realm.where(GameState::class.java).equalTo("id", userId).findFirst()
    }
}