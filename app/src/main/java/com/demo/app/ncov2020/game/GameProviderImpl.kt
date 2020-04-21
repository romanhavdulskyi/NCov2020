package com.demo.app.ncov2020.game

import com.demo.app.ncov2020.logic.Country.Country
import com.demo.app.ncov2020.logic.Disease.Ability
import com.demo.app.ncov2020.logic.Disease.Symptom
import com.demo.app.ncov2020.logic.MainPart.GameStateCallbackDecorator

class GameProviderImpl(private val gameStateCallbackDecorator: GameStateCallbackDecorator) : GameProvider {
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
        val INSTANCE = GameProviderImpl(GameStateCallbackDecorator.init(null, null))
    }

    companion object {
        val INSTANCE: GameProvider by lazy { HOLDER.INSTANCE }
    }


}