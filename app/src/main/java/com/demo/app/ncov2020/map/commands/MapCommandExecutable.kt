package com.demo.app.ncov2020.map.commands

import com.demo.app.ncov2020.common.ColorUtils
import com.demo.app.ncov2020.map.MapCountryData
import com.mapbox.geojson.Polygon
import com.mapbox.mapboxsdk.style.layers.FillLayer
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import timber.log.Timber
import java.util.*

interface MapCommandExecutable {
    fun addInfectedCountry(mapCountryData: MapCountryData)

    fun updateInfectedCountry(mapCountryData: MapCountryData)

    fun removeInfectedCountry(mapCountryData: MapCountryData)
}