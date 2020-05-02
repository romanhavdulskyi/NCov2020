package com.demo.app.ncov2020.map

import com.mapbox.geojson.Point

data class MapCountryData(val countryName:String, val points: MutableList<MutableList<Point>>, val infectedCoefficient : Double) {

    override fun toString(): String {
        return "$countryName $infectedCoefficient"
    }

}