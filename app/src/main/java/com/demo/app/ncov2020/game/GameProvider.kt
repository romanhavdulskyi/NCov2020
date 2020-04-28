package com.demo.app.ncov2020.game


import com.demo.app.ncov2020.logic.Country.Country
import com.demo.app.ncov2020.logic.Disease.Ability
import com.demo.app.ncov2020.logic.Disease.Symptom
import com.demo.app.ncov2020.logic.Disease.Transmission

interface GameProvider {
    fun initGame(guid : String)
    fun infectCountry(countryName: String)
    fun addSymptom(symptom: Symptom)
    fun addAbility(ability : Ability)
    fun addTransmission(transmission: Transmission)
    fun addClient(client: Client)
    fun removeClient()

    interface Client {
        fun onChanged(state : Game)
    }
}