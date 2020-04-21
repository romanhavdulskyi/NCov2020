package com.demo.app.ncov2020.common.offlinegeocoder

import com.mapbox.geojson.Point
import java.util.*

class Polygon {
    var points: List<Point>

    constructor(points: List<Point>) {
        this.points = points
    }

    constructor() {
        points = ArrayList()
    }

}