package com.demo.app.ncov2020.map


data class MapState(
        var loadPercentage:Int,
        var isLoading:Boolean,
        var removeCountry: MutableList<MapCountryData>,
        var addCountry: MutableList<MapCountryData>,
        var updateCountry: MutableList<MapCountryData>,
        var currDate : String,
        var upgradePoints : String,
        var selectStrategyMode : Boolean = false,
        var showMessage : Boolean = false,
        var messageText : String)
