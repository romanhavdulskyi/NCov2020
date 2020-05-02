package com.demo.app.ncov2020.map


data class Map(
        var loadPercentage:Int,
        var isLoading:Boolean,
        var removeCountry: MutableList<MapCountryData>,
        var addCountry: MutableList<MapCountryData>,
        var updateCountry: MutableList<MapCountryData>,
        var currDate : String,
        var upgradePoints : String)
