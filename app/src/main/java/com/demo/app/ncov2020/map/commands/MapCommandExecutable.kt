package com.demo.app.ncov2020.map.commands

import com.demo.app.ncov2020.map.MapCountryData

interface MapCommandExecutable {
    fun addInfectedCountry(mapCountryData: MapCountryData)

    fun updateInfectedCountry(mapCountryData: MapCountryData)

    fun removeInfectedCountry(mapCountryData: MapCountryData)
}