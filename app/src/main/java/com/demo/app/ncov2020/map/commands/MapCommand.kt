package com.demo.app.ncov2020.map.commands

import com.demo.app.ncov2020.map.MapCountryData
import com.demo.app.ncov2020.map.MapView

interface MapCommand {
        fun execute(mapCommandExecutable: MapCommandExecutable)
}