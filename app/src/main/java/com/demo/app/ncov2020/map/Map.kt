package com.demo.app.ncov2020.map

import com.mapbox.geojson.Point

data class Map(
        var loadPercentage:Int,
        var isLoading:Boolean,
        var hardInfectedPoints: MutableList<MutableList<MutableList<Point>>> = mutableListOf(),
        var mediumInfectedPoints: MutableList<MutableList<MutableList<Point>>> = mutableListOf(),
        var lowInfectedPoints: MutableList<MutableList<MutableList<Point>>> = mutableListOf())
