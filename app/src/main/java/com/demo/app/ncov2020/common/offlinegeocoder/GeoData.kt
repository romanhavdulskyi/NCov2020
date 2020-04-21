package com.demo.app.ncov2020.common.offlinegeocoder

interface GeoData {
    fun addPolygon(polygon: Polygon?)
    fun getPolygons(): MutableList<Polygon?>?
    fun setPolygons(polygons: MutableList<Polygon?>?)
}