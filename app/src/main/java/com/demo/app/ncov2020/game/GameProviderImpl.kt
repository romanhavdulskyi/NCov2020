package com.demo.app.ncov2020.game

import com.demo.app.ncov2020.data.AppDatabase
import com.demo.app.ncov2020.data.CurrentUserRepo
import com.demo.app.ncov2020.logic.Callback.ConcreateCallback
import com.demo.app.ncov2020.logic.Disease.Ability
import com.demo.app.ncov2020.logic.Disease.Symptom
import com.demo.app.ncov2020.logic.MainPart.Country
import com.demo.app.ncov2020.logic.MainPart.GameModel

class GameProviderImpl(private val gameModel: GameModel) : GameProvider {
    override fun initGame() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun infectCountry(country: Country) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addSymptom(symptom: Symptom) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addAbility(ability: Ability) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private object HOLDER {
        val INSTANCE = GameProviderImpl(GameModel(null, null))
    }

    companion object {
        val INSTANCE: GameProvider by lazy { HOLDER.INSTANCE }
    }


}