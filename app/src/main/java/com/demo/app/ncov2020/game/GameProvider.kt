package com.demo.app.ncov2020.game


import com.demo.app.ncov2020.logic.Disease.Ability
import com.demo.app.ncov2020.logic.Disease.Symptom
import com.demo.app.ncov2020.logic.MainPart.Country

interface GameProvider {
    fun initGame()
    fun infectCountry(country: Country)
    fun addSymptom(symptom: Symptom)
    fun addAbility(ability : Ability)

    interface Client {
        fun onChanged(state : Game)
    }
}