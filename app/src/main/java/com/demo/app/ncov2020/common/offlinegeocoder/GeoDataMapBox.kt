package com.demo.app.ncov2020.common.offlinegeocoder

import java.util.*

class GeoDataMapBox : GeoData {
    private var polygons: MutableList<Polygon?>?

    constructor(polygons: MutableList<Polygon?>) {
        this.polygons = polygons
    }

    constructor() {
        polygons = ArrayList()
    }

    override fun addPolygon(polygon: Polygon?) {
        polygons?.add(polygon)
    }

    override fun getPolygons(): MutableList<Polygon?>? {
        return polygons
    }

    override fun setPolygons(polygons: MutableList<Polygon?>?) {
        this.polygons = polygons
    }
}