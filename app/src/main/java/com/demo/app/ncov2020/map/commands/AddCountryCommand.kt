package com.demo.app.ncov2020.map.commands

import com.demo.app.ncov2020.map.MapCountryData

class AddCountryCommand(private val mapCountryData: MapCountryData) :  MapCommand{
    override fun execute(mapCommandExecutable: MapCommandExecutable) {
        mapCommandExecutable.addInfectedCountry(mapCountryData)
    }
}