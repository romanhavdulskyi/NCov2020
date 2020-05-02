package com.demo.app.ncov2020.common

import com.demo.app.ncov2020.common.offlinegeocoder.GeoDataFactory
import com.mapbox.geojson.Point

object MapBoxUtils {

    fun getPointsForCountry(countryName: String): MutableList<MutableList<Point>> {
        val points = mutableListOf<MutableList<Point>>()
        val data = GeoDataFactory.getPolygon(countryName)
        if (data.getPolygons()!!.isNotEmpty()) {
            points.clear()
            for (item in data.getPolygons()!!)
                points.add(item!!.points as MutableList<Point>)
        }
        return points
    }

}