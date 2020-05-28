package com.demo.app.ncov2020.game


import com.demo.app.ncov2020.logic.Disease.Ability
import com.demo.app.ncov2020.logic.Disease.Symptom
import com.demo.app.ncov2020.logic.Disease.Transmission
import com.demo.app.ncov2020.logic.MainPart.Strategy

interface GameProvider {
    fun initGame(guid : String)
    fun infectCountry(countryName: String)
    fun addSymptom(symptom: Symptom)
    fun addAbility(ability : Ability)
    fun addTransmission(transmission: Transmission)
    fun addClient(client: Client)
    fun removeClient(client: Client)
    fun notifyAll(game: Game)
    fun loadSnapshot()
    fun makeSnapshot()

    /*
    Firstly, I want to tell U sorry about that shitty code, but it`s the last night before demo release, that`s why I need to do it in this way...
    Secondly,
     */
    fun setStrategy(strategy: Strategy)
    fun setCountryForStrategy(countryName: String)

    interface Client {
        fun onChanged(state : Game)
    }
}